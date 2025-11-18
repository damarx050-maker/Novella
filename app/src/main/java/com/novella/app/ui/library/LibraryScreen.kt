package com.novella.app.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LibraryScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "استمر في القراءة")
        Text(text = "المحمّل أوفلاين")
        Text(text = "المفضلة")
    }
}
