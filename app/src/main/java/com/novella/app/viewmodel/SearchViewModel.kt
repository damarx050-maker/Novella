package com.novella.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novella.app.data.local.entities.NovelEntity
import com.novella.app.data.remote.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val remote: FirestoreService) : ViewModel() {
    private val _results = MutableStateFlow<List<NovelEntity>>(emptyList())
    val results: StateFlow<List<NovelEntity>> = _results.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            remote.searchByPrefix("title", query).collect { _results.value = it }
        }
    }
}
