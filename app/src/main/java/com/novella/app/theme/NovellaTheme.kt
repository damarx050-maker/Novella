package com.novella.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun NovellaTheme(
    isArabic: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = darkColorScheme(
        primary = NovellaPrimary,
        secondary = NovellaSecondary,
        background = NovellaBackground,
        surface = NovellaSurface,
        surfaceVariant = NovellaSurfaceVariant,
        onSurface = NovellaOnSurface,
        outline = NovellaOutline,
        error = NovellaError
    )

    val typography = if (isArabic) TypographyAR else TypographyEN

    var layout by remember(isArabic) { mutableStateOf(if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr) }
    CompositionLocalProvider(LocalLayoutDirection provides layout) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = NovellaShapes,
            content = content
        )
    }
}
