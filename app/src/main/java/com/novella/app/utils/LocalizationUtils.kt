package com.novella.app.utils

import android.content.Context
import android.content.SharedPreferences

object LocalizationUtils {
    private const val PREFS = "novella_prefs"
    private const val KEY_LANG = "lang"
    const val AR = "AR"
    const val EN = "EN"

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getLanguage(context: Context): String = getPrefs(context).getString(KEY_LANG, EN) ?: EN

    fun setLanguage(context: Context, lang: String) {
        getPrefs(context).edit().putString(KEY_LANG, lang).apply()
    }
}
