package com.novella.app.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.novella.app.viewmodel.BillingViewModel
import com.novella.app.billing.BillingProductIds
import android.app.Activity

@Composable
fun DetailsScreen(novelId: String, onRead: () -> Unit, vm: DetailsViewModel = hiltViewModel(), storage: StorageService = StorageService(), billingVm: BillingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val novel: NovelEntity? by vm.novel(novelId).collectAsState(initial = null)
    LaunchedEffect(Unit) { billingVm.start() }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            AsyncImage(model = novel?.coverUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(220.dp))
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = novel?.title ?: "")
                    if (novel != null) {
                        val accessState by vm.canAccess(novel!!).collectAsState(initial = false)
                        if (accessState) {
                            Text(text = "مدفوع", color = Color(0xFF4CAF50))
                        }
                    }
                }
                Text(text = novel?.author ?: "")
                Spacer(Modifier.height(8.dp))
                Text(text = novel?.description ?: "")
                Spacer(Modifier.height(16.dp))
                val scope = rememberCoroutineScope()
                val downloadedText = UiStrings.downloaded()
                val retryText = UiStrings.retry()
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = onRead, colors = ButtonDefaults.buttonColors(containerColor = NovellaPrimary), modifier = Modifier.weight(1f).height(48.dp)) {
                        Text(UiStrings.startReading(), color = Color.White)
                    }
                    Button(onClick = {
                        val act = context as? Activity ?: return@Button
                        billingVm.buySingle(act, BillingProductIds.SINGLE_NOVEL)
                    }, modifier = Modifier.height(48.dp)) { Text("شراء") }
                    Button(onClick = {
                        val act = context as? Activity ?: return@Button
                        billingVm.subscribeMonthly(act, BillingProductIds.MONTHLY_SUB)
                    }, modifier = Modifier.height(48.dp)) { Text("اشتراك شهري") }
                    Button(onClick = {
                        val act = context as? Activity ?: return@Button
                        billingVm.subscribeMonthly(act, BillingProductIds.YEARLY_SUB)
                    }, modifier = Modifier.height(48.dp)) { Text("اشتراك سنوي") }
                    Button(onClick = {
                        val n = novel ?: return@Button
                        scope.launch {
                            try {
                                val file = withContext(Dispatchers.IO) { storage.downloadPdfToLocal(context, n.id, n.pdfUrl) }
                                vm.markDownloaded(n, file.absolutePath)
                                snackbarHostState.showSnackbar(downloadedText)
                                onRead()
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("$retryText: ${e.message ?: ""}")
                            }
                        }
                    }, modifier = Modifier.weight(1f).height(48.dp)) {
                        Text(UiStrings.downloadOffline())
                    }
                }
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(progress = { 0.0f })
            }
        }
    }
}
