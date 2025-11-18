package com.novella.app.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.novella.app.billing.BillingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(private val billing: BillingManager) : ViewModel() {
    fun start() = billing.start()
    fun buySingle(activity: Activity, productId: String) = billing.launchPurchase(activity, productId, isSubscription = false)
    fun subscribeMonthly(activity: Activity, productId: String) = billing.launchPurchase(activity, productId, isSubscription = true)

    // Expose flows
    val isSubscribed: StateFlow<Boolean> get() = billing.isSubscribed
    val ownedSkus: StateFlow<Set<String>> get() = billing.ownedSkus
    val purchaseEvents: SharedFlow<com.android.billingclient.api.Purchase> get() = billing.purchaseEvents
    val billingErrors: SharedFlow<String> get() = billing.billingErrors
}
