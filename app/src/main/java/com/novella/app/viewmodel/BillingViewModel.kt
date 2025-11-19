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
    fun subscribeMonthly(activity: Activity, productId: String) = billing.launchSubscription(activity, productId)
    fun subscribeYearly(activity: Activity, productId: String) = billing.launchSubscription(activity, productId)

    // Expose flows
    val isSubscribed: StateFlow<Boolean> get() = billing.isSubscribed
    val billingErrors: SharedFlow<String> get() = billing.billingErrors
}
