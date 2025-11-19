package com.novella.app.ui.novel

import androidx.compose.runtime.Composable
import com.novella.app.ui.details.DetailsScreen

@Composable
fun NovelDetailScreen(
    novelId: String,
    onRead: () -> Unit
) {
    DetailsScreen(novelId = novelId, onRead = onRead)
}
