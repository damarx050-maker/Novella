package com.novella.app.billing

import android.app.Application
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.PurchasesUpdatedListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayBillingClient(
    app: Application,
    private val onPurchases: (List<com.android.billingclient.api.Purchase>) -> Unit = {},
    private val onError: (com.android.billingclient.api.BillingResult) -> Unit = {}
) {
    private val listener = PurchasesUpdatedListener { result, purchases ->
        if (result.responseCode == BillingResponseCode.OK && purchases != null) onPurchases(purchases)
        else onError(result)
    }
    private val _ready = MutableStateFlow(false)
    val ready: StateFlow<Boolean> = _ready

    val client: BillingClient = BillingClient.newBuilder(app)
        .setListener(listener)
        .enablePendingPurchases()
        .build()

    fun startConnection() {
        client.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() { _ready.value = false }
            override fun onBillingSetupFinished(result: com.android.billingclient.api.BillingResult) {
                _ready.value = result.responseCode == BillingResponseCode.OK
            }
        })
    }
}
