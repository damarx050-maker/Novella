package com.novella.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.novella.app.theme.NovellaBackground
import com.novella.app.theme.NovellaPrimary
import com.novella.app.utils.UiStrings

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NovellaBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            Card(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val text = when (page) {
                        0 -> "اقرأ آلاف الروايات بأسلوب Novella"
                        1 -> "اشترك شهريًا 15 درهم أو سنويًا 135 درهم عبر Novella"
                        else -> "اشترِ رواية منفردة بـ 5 دراهم من Novella"
                    }
                    Text(text)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { idx ->
                val selected = pagerState.currentPage == idx
                Box(
                    Modifier.size(if (selected) 10.dp else 8.dp)
                        .background(if (selected) NovellaPrimary else Color.Gray, shape = androidx.compose.foundation.shape.CircleShape)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        if (pagerState.currentPage == 2) {
            Button(onClick = onDone, colors = ButtonDefaults.buttonColors(containerColor = NovellaPrimary), modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Text(text = UiStrings.startNow(), color = Color.White)
            }
        }
    }
}
