package com.novella.app.billing

import android.app.Application
import android.app.Activity
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.AcknowledgePurchaseParams
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
    private val _activeSubscriptions = MutableStateFlow<List<Purchase>>(emptyList())
    val activeSubscriptions: StateFlow<List<Purchase>> = _activeSubscriptions

    // Public flows per spec
    val isSubscribed: StateFlow<Boolean> = MutableStateFlow(false)
    private val _ownedSkus = MutableStateFlow<Set<String>>(emptySet())
    val ownedSkus: StateFlow<Set<String>> = _ownedSkus
    private val _purchaseEvents = kotlinx.coroutines.flow.MutableSharedFlow<Purchase>(extraBufferCapacity = 8)
    val purchaseEvents: kotlinx.coroutines.flow.SharedFlow<Purchase> = _purchaseEvents
    private val _billingErrors = kotlinx.coroutines.flow.MutableSharedFlow<String>(extraBufferCapacity = 8)
    val billingErrors: kotlinx.coroutines.flow.SharedFlow<String> = _billingErrors

    private val client = PlayBillingClient(
        app,
        onPurchases = { purchases ->
            scope.launch {
                purchases.forEach { _purchaseEvents.tryEmit(it) }
                persistPurchases(purchases)
            }
        },
        onError = { result ->
            _billingErrors.tryEmit("${'$'}{result.responseCode}: ${'$'}{result.debugMessage}")
        }
    )

    fun start() { client.startConnection() }

    private suspend fun persistPurchases(purchases: List<Purchase>) {
        purchases.forEach { p ->
            val type = if (p.products.any { it.contains("monthly", true) || it.contains("yearly", true) }) "SUBSCRIPTION" else "ONE_TIME"
            val status = if (p.isAcknowledged) "ACTIVE" else "PENDING"
            val id = p.products.firstOrNull() ?: return@forEach
            repo.persistPurchase(PurchaseEntity(productId = id, type = type, status = status))
            if (!p.isAcknowledged && type == "SUBSCRIPTION") {
                acknowledge(p)
                repo.persistPurchase(PurchaseEntity(productId = id, type = type, status = "ACTIVE"))
            }
        }
        // Update owned SKUs snapshot
        _ownedSkus.value = purchases.flatMap { it.products }.toSet()
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

    fun launchPurchase(activity: Activity, productId: String, isSubscription: Boolean) {
        scope.launch {
            val type = if (isSubscription) BillingClient.ProductType.SUBS else BillingClient.ProductType.INAPP
            val details = queryProductDetails(listOf(productId), type).firstOrNull() ?: return@launch
            val offerToken = details.subscriptionOfferDetails?.firstOrNull()?.offerToken
            val productParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(details)
                .apply { if (offerToken != null) setOfferToken(offerToken) }
                .build()
            val flowParams = BillingFlowParams.newBuilder().setProductDetailsParamsList(listOf(productParams)).build()
            client.client.launchBillingFlow(activity, flowParams)
        }
    }
}
