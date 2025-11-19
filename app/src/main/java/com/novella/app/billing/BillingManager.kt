package com.novella.app.billing

import android.app.Application
import android.app.Activity
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.QueryPurchasesParams
import com.novella.app.data.local.entities.PurchaseEntity
import com.novella.app.data.repo.BillingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class BillingManager(app: Application, private val repo: BillingRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    // Public flows for subscription-only
    private val _isSubscribed = MutableStateFlow(false)
    val isSubscribed: StateFlow<Boolean> = _isSubscribed
    private val _billingErrors = kotlinx.coroutines.flow.MutableSharedFlow<String>(extraBufferCapacity = 8)
    val billingErrors: kotlinx.coroutines.flow.SharedFlow<String> = _billingErrors

    private val client = PlayBillingClient(
        app,
        onPurchases = { purchases ->
            scope.launch {
                // Persist only subscription purchases
                val subs = purchases.filter { it.products.any { _ -> true } } // will re-validate below
                persistSubscriptionPurchases(subs)
            }
        },
        onError = { result ->
            _billingErrors.tryEmit("${'$'}{result.responseCode}: ${'$'}{result.debugMessage}")
        }
    )

    fun start() {
        client.startConnection()
        // Track subscription entitlement from repository
        scope.launch {
            repo.isSubscriptionActive().collect { _isSubscribed.value = it }
        }
        // Restore purchases on startup
        scope.launch { restorePurchases() }
    }

    private suspend fun persistSubscriptionPurchases(purchases: List<Purchase>) {
        purchases.forEach { p ->
            // Keep only products that look like subscription SKUs
            val isSub = p.products.any { it.contains("monthly", true) || it.contains("yearly", true) }
            if (!isSub) return@forEach
            val status = if (p.isAcknowledged) "ACTIVE" else "PENDING"
            val id = p.products.firstOrNull() ?: return@forEach
            repo.persistPurchase(PurchaseEntity(productId = id, type = "SUBSCRIPTION", status = status))
            if (!p.isAcknowledged) {
                acknowledge(p)
                repo.persistPurchase(PurchaseEntity(productId = id, type = "SUBSCRIPTION", status = "ACTIVE"))
            }
        }
    }

    private fun acknowledge(purchase: Purchase) {
        val params = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        client.client.acknowledgePurchase(params) { /* result ignored here; repo status updated optimistically */ }
    }

    suspend fun queryProductDetails(productIds: List<String>, type: String): List<ProductDetails> = suspendCancellableCoroutine { cont ->
        val products = productIds.map { pid ->
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(pid)
                .setProductType(type)
                .build()
        }
        val params = QueryProductDetailsParams.newBuilder().setProductList(products).build()
        client.client.queryProductDetailsAsync(params) { result, list ->
            if (!cont.isCompleted) cont.resume(list ?: emptyList())
        }
    }

    fun launchSubscription(activity: Activity, productId: String) {
        scope.launch {
            val details = queryProductDetails(listOf(productId), BillingClient.ProductType.SUBS).firstOrNull() ?: return@launch
            val offerToken = details.subscriptionOfferDetails?.firstOrNull()?.offerToken
            val productParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(details)
                .apply { if (offerToken != null) setOfferToken(offerToken) }
                .build()
            val flowParams = BillingFlowParams.newBuilder().setProductDetailsParamsList(listOf(productParams)).build()
            client.client.launchBillingFlow(activity, flowParams)
        }
    }

    suspend fun restorePurchases() {
        // Query active SUBS only
        val subsResult = suspendCancellableCoroutine<List<Purchase>> { cont ->
            val params = QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
            client.client.queryPurchasesAsync(params) { _, purchases ->
                if (!cont.isCompleted) cont.resume(purchases ?: emptyList())
            }
        }
        persistSubscriptionPurchases(subsResult)
    }
}
