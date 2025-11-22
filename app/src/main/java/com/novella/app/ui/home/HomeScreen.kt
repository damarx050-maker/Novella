@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.novella.app.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.novella.app.data.local.entities.NovelEntity
import com.novella.app.utils.UiStrings
import com.novella.app.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.background

@Composable
fun HomeScreen(
    onOpenDetails: (String) -> Unit,
    onOpenSearch: () -> Unit
) {
    val vm: HomeViewModel = hiltViewModel()
    val billingVm: com.novella.app.viewmodel.BillingViewModel = hiltViewModel()
    val new by vm.new.collectAsState()
    val popular by vm.popular.collectAsState()
    val philosophy by vm.philosophy.collectAsState()
    val horror by vm.horror.collectAsState()
    val romance by vm.romance.collectAsState()
    val historical by vm.historical.collectAsState()
    val fantasy by vm.fantasy.collectAsState()
    val isRefreshing by vm.isRefreshing.collectAsState()
    val loadError by vm.loadError.collectAsState()
    val isSubscribed by billingVm.isSubscribed.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val hasAnyData = new.isNotEmpty() || popular.isNotEmpty() || philosophy.isNotEmpty() || horror.isNotEmpty() || romance.isNotEmpty() || historical.isNotEmpty() || fantasy.isNotEmpty()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        // Ensure BillingClient connection and restore on first entry
        androidx.compose.runtime.LaunchedEffect(Unit) { billingVm.start() }
        val pullState = rememberPullToRefreshState()
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { vm.refresh() },
            state = pullState,
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            Column(Modifier.fillMaxSize()) {
                // Show top progress bar only when refreshing with existing data
                if (isRefreshing && hasAnyData) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(2.dp))
                }
                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    val showPlaceholders = isRefreshing && !hasAnyData
                    SectionRow("NEW", title = "الجديدة", items = new, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                    SectionRow("POPULAR", title = "الشائعة", items = popular, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                    SectionRow("PHILOSOPHY", title = "الفلسفة", items = philosophy, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                    SectionRow("HORROR", title = "الرعب", items = horror, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                    SectionRow("ROMANCE", title = "الرومانسية (حب)", items = romance, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                    SectionRow("HISTORICAL", title = "التاريخي", items = historical, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                    SectionRow("FANTASY", title = "الفنتازيا", items = fantasy, onClick = onOpenDetails, isLoading = showPlaceholders, vm = vm, isSubscribed = isSubscribed)
                }
            }
        }
    }

    // Trigger initial refresh to populate data on first entry
    androidx.compose.runtime.LaunchedEffect(Unit) {
        vm.refresh()
    }

    // Show snackbar on load error with Retry action
    if (loadError) {
        androidx.compose.runtime.LaunchedEffect(loadError) {
            val result = snackbarHostState.showSnackbar(
                message = "تعذر تحميل الروايات، تحقق من الاتصال أو أعد المحاولة",
                actionLabel = "إعادة المحاولة",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                scope.launch { vm.refresh() }
            }
        }
    }
}

@Composable
private fun SectionRow(
    category: String,
    title: String,
    items: List<NovelEntity>,
    onClick: (String) -> Unit,
    isLoading: Boolean = false,
    vm: HomeViewModel,
    isSubscribed: Boolean = false
) {
    Text(text = title)
    Spacer(Modifier.height(8.dp))
    val categoryRefreshing by vm.categoryRefreshing.collectAsState()
    val categoryErrors by vm.categoryErrors.collectAsState()
    val isSectionRefreshing = categoryRefreshing.contains(category)
    val hasError = categoryErrors.contains(category)
    if (isSectionRefreshing) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(2.dp))
    }
    val placeholderColor = if (isSystemInDarkTheme()) Color.DarkGray.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.3f)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        if (isLoading) {
            items(6) { _ ->
                Column(modifier = Modifier.width(140.dp)) {
                    ShimmerBlock(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(Modifier.height(6.dp))
                    ShimmerBlock(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    ShimmerBlock(
                        modifier = Modifier
                            .height(14.dp)
                            .fillMaxWidth(0.6f),
                        shape = RoundedCornerShape(4.dp)
                    )
                }
            }
        } else if (hasError) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = "تعذر تحميل هذا القسم")
                    Spacer(Modifier.width(12.dp))
                    Button(onClick = { vm.refreshCategory(category) }) {
                        Text(text = UiStrings.retry())
                    }
                }
            }
        } else if (items.isEmpty()) {
            item {
                Text(text = UiStrings.emptySection(), modifier = Modifier.padding(vertical = 8.dp))
            }
        } else {
            items(items) { novel ->
                Column(modifier = Modifier.width(140.dp).clickable { onClick(novel.id) }) {
                    androidx.compose.foundation.layout.Box {
                        AsyncImage(
                        model = novel.coverUrl,
                        contentDescription = novel.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f),
                        contentScale = ContentScale.Crop
                        )
                        // Show badge when novel is premium and user lacks entitlement
                        val showBadge = novel.isPremium && !isSubscribed
                        if (showBadge) {
                            com.novella.app.ui.components.PremiumBadge(
                                modifier = Modifier.align(Alignment.TopEnd).padding(6.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(text = novel.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = novel.author, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(2.dp))
                    Text(text = novel.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun ShimmerBlock(modifier: Modifier, shape: androidx.compose.ui.graphics.Shape) {
    val isDark = isSystemInDarkTheme()
    val baseColor = if (isDark) Color.DarkGray.copy(alpha = 0.25f) else Color.LightGray.copy(alpha = 0.25f)
    val highlightColor = if (isDark) Color.Gray.copy(alpha = 0.35f) else Color.White.copy(alpha = 0.6f)
    val transition = rememberInfiniteTransition(label = "shimmer")
    val x by transition.animateFloat(
        initialValue = -300f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "x"
    )
    val brush = Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = androidx.compose.ui.geometry.Offset(x, 0f),
        end = androidx.compose.ui.geometry.Offset(x + 300f, 0f)
    )
    Box(
        modifier
            .clip(shape)
            .background(brush)
    )
}

// SnackbarHost is provided from Scaffold in HomeScreen for unified messaging
