package com.novella.app.data.repo

import android.content.Context
import androidx.core.content.edit

class SettingsRepository(context: Context) {
    private val prefs = context.getSharedPreferences("novella_prefs", Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(value) = prefs.edit { putBoolean("isLoggedIn", value) }

    var userId: String?
        get() = prefs.getString("userId", null)
        set(value) = prefs.edit { putString("userId", value) }

    var onboardingShown: Boolean
        get() = prefs.getBoolean("onboarding_shown", false)
        set(value) = prefs.edit { putBoolean("onboarding_shown", value) }
}
