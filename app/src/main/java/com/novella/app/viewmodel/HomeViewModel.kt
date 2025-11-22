package com.novella.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novella.app.data.local.entities.NovelEntity
import com.novella.app.data.repo.NovelsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: NovelsRepository) : ViewModel() {
    val new: StateFlow<List<NovelEntity>> = repo.novelsByCategory("NEW").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val popular: StateFlow<List<NovelEntity>> = repo.novelsByCategory("POPULAR").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val philosophy: StateFlow<List<NovelEntity>> = repo.novelsByCategory("PHILOSOPHY").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val horror: StateFlow<List<NovelEntity>> = repo.novelsByCategory("HORROR").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val romance: StateFlow<List<NovelEntity>> = repo.novelsByCategory("ROMANCE").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val historical: StateFlow<List<NovelEntity>> = repo.novelsByCategory("HISTORICAL").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val fantasy: StateFlow<List<NovelEntity>> = repo.novelsByCategory("FANTASY").stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val loadError = MutableStateFlow(false)
    val isRefreshing = MutableStateFlow(false)

    private val _categoryRefreshing = MutableStateFlow<Set<String>>(emptySet())
    val categoryRefreshing: StateFlow<Set<String>> = _categoryRefreshing
    private val _categoryErrors = MutableStateFlow<Set<String>>(emptySet())
    val categoryErrors: StateFlow<Set<String>> = _categoryErrors

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            loadError.value = false
            val categories = listOf(
                "NEW", "POPULAR", "PHILOSOPHY", "HORROR", "ROMANCE", "HISTORICAL", "FANTASY"
            )
            val failed = mutableSetOf<String>()
            for (c in categories) {
                val r = repo.refreshCategory(c)
                if (r.isFailure) failed.add(c)
            }
            _categoryErrors.value = failed
            loadError.value = failed.isNotEmpty()
            isRefreshing.value = false
        }
    }

    fun refreshCategory(category: String) {
        viewModelScope.launch {
            _categoryErrors.value = _categoryErrors.value - category
            _categoryRefreshing.value = _categoryRefreshing.value + category
            val result = repo.refreshCategory(category)
            if (result.isFailure) {
                _categoryErrors.value = _categoryErrors.value + category
            }
            _categoryRefreshing.value = _categoryRefreshing.value - category
        }
    }
}
