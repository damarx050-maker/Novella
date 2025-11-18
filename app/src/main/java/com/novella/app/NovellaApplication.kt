package com.novella.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NovellaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase safely; will no-op if google-services isn't set up yet
        try {
            FirebaseApp.initializeApp(this)
        } catch (_: Throwable) {
            // Ignore until google-services.json is provided
        }
    }
}
