package com.novella.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novella.app.data.repo.NovelsRepository
import com.novella.app.data.local.dao.DownloadsDao
import com.novella.app.data.local.entities.DownloadEntity
import com.novella.app.data.remote.StorageService
import com.novella.app.data.local.entities.NovelEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val repo: NovelsRepository,
    private val downloadsDao: DownloadsDao,
    private val storageService: StorageService
) : ViewModel() {
    private val _nightMode = MutableStateFlow(false)
    val nightMode: StateFlow<Boolean> = _nightMode

    fun toggleNightMode() { _nightMode.value = !_nightMode.value }

    fun saveProgress(novelId: String, current: Int, total: Int) {
        viewModelScope.launch { repo.updateProgress(novelId, current, total) }
    }

    suspend fun getLocalDownload(novelId: String): DownloadEntity? = downloadsDao.get(novelId).first()

    fun addBookmark(novelId: String, page: Int, note: String?) {
        viewModelScope.launch { repo.addBookmark(novelId, page, note) }
    }

    fun addQuote(novelId: String, page: Int, text: String) {
        viewModelScope.launch { repo.addQuote(novelId, page, text) }
    }

    fun novel(id: String) = repo.novel(id)
    fun canAccess(novel: NovelEntity) = repo.canAccess(novel)
}
