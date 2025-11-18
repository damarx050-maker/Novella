package com.novella.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.novella.app.theme.NovellaTheme
import com.novella.app.ui.NovellaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovellaTheme {
                NovellaApp()
            }
        }
    }
}
