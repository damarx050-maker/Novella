package com.novella.app.ui.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.novella.app.theme.ReaderDayBackground
import com.novella.app.theme.ReaderDayText
import com.novella.app.theme.ReaderNightBackground
import com.novella.app.theme.ReaderNightText
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.novella.app.viewmodel.ReaderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.IOException

@Composable
fun ReaderScreen(novelId: String, vm: ReaderViewModel = hiltViewModel()) {
    val nightState = remember { mutableStateOf(false) }
    val bg = if (nightState.value) ReaderNightBackground else ReaderDayBackground
    val fg = if (nightState.value) ReaderNightText else ReaderDayText
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var filePath by remember { mutableStateOf<String?>(null) }
    var pageCount by remember { mutableStateOf(0) }
    var currentPage by remember { mutableStateOf(0) }
    var scale by remember { mutableStateOf(1f) }
    val transformState = rememberTransformableState { zoomChange, _, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 4f)
    }
    var addQuoteDialog by remember { mutableStateOf(false) }
    var quoteText by remember { mutableStateOf("") }

    val novel by vm.novel(novelId).collectAsState(initial = null)
    val accessState = if (novel != null) vm.canAccess(novel!!).collectAsState(initial = false).value else false

    LaunchedEffect(novelId) {
        val download = vm.getLocalDownload(novelId)
        if (download?.localPath != null) {
            filePath = download.localPath
        } else {
            snackbarHostState.showSnackbar("يرجى تحميل الرواية للقراءة أوفلاين")
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().background(bg).padding(padding).padding(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth().transformable(transformState), contentAlignment = Alignment.Center) {
                if (!accessState) {
                    Text(text = "هذه الرواية غير متاحة. يرجى الشراء أو الاشتراك.")
                } else {
                    val path = filePath
                    if (path != null) {
                        PdfPageView(filePath = path, pageIndex = currentPage, scale = scale) { count -> pageCount = count }
                        LaunchedEffect(currentPage, pageCount) {
                            vm.saveProgress(novelId, currentPage + 1, pageCount)
                        }
                    } else {
                        Text(text = "لا يوجد ملف محلي")
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "صفحة ${'$'}{currentPage + 1} / ${'$'}pageCount", color = fg)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { if (currentPage > 0) currentPage-- }) { Text("السابق") }
                    Button(onClick = { if (currentPage < pageCount - 1) currentPage++ }) { Text("التالي") }
                    Button(onClick = { nightState.value = !nightState.value }) { Text(if (nightState.value) "نهاري" else "ليلي") }
                    Button(onClick = { addQuoteDialog = true }) { Text("اقتباس") }
                    Button(onClick = {
                        scope.launch {
                            vm.addBookmark(novelId, currentPage + 1, null)
                            snackbarHostState.showSnackbar("تم حفظ العلامة المرجعية")
                        }
                    }) { Text("إشارة مرجعية") }
                }
            }
        }
    }

    if (addQuoteDialog) {
        AlertDialog(
            onDismissRequest = { addQuoteDialog = false },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        vm.addQuote(novelId, currentPage + 1, quoteText)
                        snackbarHostState.showSnackbar("تم حفظ الاقتباس")
                    }
                    addQuoteDialog = false
                }) { Text("حفظ") }
            },
            dismissButton = { Button(onClick = { addQuoteDialog = false }) { Text("إلغاء") } },
            title = { Text("إضافة اقتباس") },
            text = { OutlinedTextField(value = quoteText, onValueChange = { quoteText = it }) }
        )
    }
}

@Composable
private fun PdfPageView(filePath: String, pageIndex: Int, scale: Float, onPageCount: (Int) -> Unit) {
    val bitmapState = remember(pageIndex, scale, filePath) { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(pageIndex, scale, filePath) {
        withContext(Dispatchers.IO) {
            try {
                val file = File(filePath)
                val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                val renderer = PdfRenderer(pfd)
                onPageCount(renderer.pageCount)
                if (pageIndex in 0 until renderer.pageCount) {
                    val page = renderer.openPage(pageIndex)
                    val width = (page.width * scale).toInt().coerceAtLeast(1)
                    val height = (page.height * scale).toInt().coerceAtLeast(1)
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()
                    withContext(Dispatchers.Main) { bitmapState.value = bmp }
                }
                renderer.close()
                pfd.close()
            } catch (_: IOException) {
                withContext(Dispatchers.Main) { bitmapState.value = null }
            }
        }
    }
    val bmp = bitmapState.value
    if (bmp != null) {
        androidx.compose.foundation.Image(
            bitmap = bmp.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        Text("تعذر عرض الصفحة")
    }
}
