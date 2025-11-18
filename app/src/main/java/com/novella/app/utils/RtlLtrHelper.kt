package com.novella.app.utils

import androidx.compose.runtime.compositionLocalOf

// CompositionLocal for current language code (AR/EN)
val LocalLanguageCode = compositionLocalOf { LocalizationUtils.EN }
