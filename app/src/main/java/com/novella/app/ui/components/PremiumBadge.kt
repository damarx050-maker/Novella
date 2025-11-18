package com.novella.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.novella.app.theme.BadgeCapsuleShape
import com.novella.app.theme.NovellaAccentGold
import com.novella.app.utils.UiStrings

@Composable
fun PremiumBadge(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
    textColor: Color = Color.Black
) {
    Box(
        modifier = modifier
            .background(NovellaAccentGold, shape = BadgeCapsuleShape)
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = UiStrings.premiumBadge(),
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}
