package com.novella.app.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.novella.app.billing.BillingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(private val billing: BillingManager) : ViewModel() {
    fun start() = billing.start()
    fun buySingle(activity: Activity, productId: String) = billing.launchPurchase(activity, productId, isSubscription = false)
    fun subscribeMonthly(activity: Activity, productId: String) = billing.launchPurchase(activity, productId, isSubscription = true)
}
