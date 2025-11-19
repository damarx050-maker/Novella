@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.novella.app.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel
import com.novella.app.data.remote.StorageService
import com.novella.app.data.local.entities.NovelEntity
import com.novella.app.viewmodel.DetailsViewModel
import com.novella.app.theme.NovellaPrimary
import com.novella.app.utils.UiStrings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    novelId: String,
    onRead: () -> Unit,
    vm: DetailsViewModel = hiltViewModel(),
    storage: StorageService = StorageService(),
    billingVm: com.novella.app.viewmodel.BillingViewModel = hiltViewModel(),
    onOpenSubscription: () -> Unit = {}
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val novel: NovelEntity? by vm.novel(novelId).collectAsState(initial = null)
    val canAccess by (novel?.let { vm.canAccess(it) } ?: kotlinx.coroutines.flow.flowOf(false)).collectAsState(initial = false)
    val showPaywall = novel != null && !canAccess
    var showSheet = remember { androidx.compose.runtime.mutableStateOf(false) }
    // Keep details screen focused and uncluttered; billing UI handled elsewhere

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            AsyncImage(
                model = novel?.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = novel?.title ?: "")
                }
                Text(text = novel?.author ?: "")
                Spacer(Modifier.height(8.dp))
                Text(text = novel?.description ?: "")
                Spacer(Modifier.height(16.dp))
                val scope = rememberCoroutineScope()
                Button(
                    onClick = {
                        if (showPaywall) {
                            showSheet.value = true
                        } else onRead()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NovellaPrimary),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(UiStrings.startReading(), color = Color.White)
                }
                if (showPaywall && showSheet.value) {
                    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                    ModalBottomSheet(
                        onDismissRequest = { showSheet.value = false },
                        sheetState = sheetState
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = {
                                val act = context as? android.app.Activity ?: return@Button
                                billingVm.subscribeMonthly(act, com.novella.app.billing.BillingProductIds.MONTHLY_SUB)
                                showSheet.value = false
                            }, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text(UiStrings.subscribeMonthly()) }
                            Button(onClick = {
                                val act = context as? android.app.Activity ?: return@Button
                                billingVm.subscribeYearly(act, com.novella.app.billing.BillingProductIds.YEARLY_SUB)
                                showSheet.value = false
                            }, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text(UiStrings.subscribeYearly()) }
                            Button(onClick = {
                                showSheet.value = false
                                onOpenSubscription()
                            }, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text(UiStrings.viewPlans()) }
                        }
                    }
                }
            }
        }
    }
}
