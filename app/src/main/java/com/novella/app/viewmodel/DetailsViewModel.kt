package com.novella.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novella.app.data.local.entities.NovelEntity
import com.novella.app.data.repo.NovelsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: NovelsRepository) : ViewModel() {
    fun novel(id: String): Flow<NovelEntity?> = repo.novel(id)

    fun canAccess(novel: NovelEntity): Flow<Boolean> = repo.canAccess(novel)

    fun markDownloaded(novel: NovelEntity, localPath: String) {
        viewModelScope.launch { repo.markDownloaded(novel, localPath) }
    }
}
