package com.novella.app.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.novella.app.data.local.entities.NovelEntity
import androidx.hilt.navigation.compose.hiltViewModel
import com.novella.app.utils.UiStrings
import com.novella.app.viewmodel.SearchViewModel

@Composable
fun SearchScreen(onOpenDetails: (String) -> Unit, vm: SearchViewModel = hiltViewModel()) {
    val results by vm.results.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var q by remember { mutableStateOf("") }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(value = q, onValueChange = { q = it; if (q.isNotBlank()) vm.search(q) }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("ابحث هنا...") })
            Spacer(Modifier.height(12.dp))
            if (q.isNotBlank() && results.isEmpty()) {
                Text(text = UiStrings.noResults())
                Spacer(Modifier.height(4.dp))
                Text(text = UiStrings.tryAnother())
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(results) { novel ->
                        Column(Modifier.fillMaxWidth()) {
                            Text(novel.title)
                            Text(novel.author)
                        }
                    }
                }
            }
        }
    }
}
