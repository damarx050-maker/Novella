package com.novella.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.novella.app.R

object UiStrings {
    @Composable fun appName() = stringResource(R.string.app_name)
    @Composable fun loginWithGoogle() = stringResource(R.string.login_with_google)
    @Composable fun welcome() = stringResource(R.string.welcome_text)
    @Composable fun startNow() = stringResource(R.string.start_now)
    @Composable fun startReading() = stringResource(R.string.start_reading)
    @Composable fun downloadOffline() = stringResource(R.string.download_offline)
    @Composable fun downloaded() = stringResource(R.string.downloaded)
    @Composable fun retry() = stringResource(R.string.retry)
    @Composable fun noResults() = stringResource(R.string.no_results)
    @Composable fun tryAnother() = stringResource(R.string.try_another)
    @Composable fun emptySection() = stringResource(R.string.empty_section)
    @Composable fun premiumBadge() = stringResource(R.string.premium_badge)
    @Composable fun buyThisNovel() = stringResource(R.string.buy_this_novel)
    @Composable fun subscribeMonthly() = stringResource(R.string.subscribe_monthly)
    @Composable fun subscribeYearly() = stringResource(R.string.subscribe_yearly)
    @Composable fun monthlyPlanTitle() = stringResource(R.string.monthly_plan_title)
    @Composable fun monthlyPlanSubtitle() = stringResource(R.string.monthly_plan_subtitle)
    @Composable fun monthlyPlanPrice() = stringResource(R.string.monthly_plan_price)
    @Composable fun yearlyPlanTitle() = stringResource(R.string.yearly_plan_title)
    @Composable fun yearlyPlanSubtitle() = stringResource(R.string.yearly_plan_subtitle)
    @Composable fun yearlyPlanPrice() = stringResource(R.string.yearly_plan_price)
    @Composable fun manageSubscription() = stringResource(R.string.manage_subscription)
    @Composable fun subscribeNow() = stringResource(R.string.subscribe_now)
}
