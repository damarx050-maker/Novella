package com.novella.app.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NetworkUtils {
    // Minimal placeholder state. In production, hook into connectivity manager.
    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline

    fun setOnline(online: Boolean) { _isOnline.value = online }
}
