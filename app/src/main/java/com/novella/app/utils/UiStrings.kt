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
}
