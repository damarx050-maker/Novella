package com.novella.app.viewmodel

import androidx.lifecycle.ViewModel
import com.novella.app.data.local.dao.DownloadsDao
import com.novella.app.data.local.dao.ProgressDao
import com.novella.app.data.local.entities.DownloadEntity
import com.novella.app.data.local.entities.ProgressEntity
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val downloadsDao: DownloadsDao,
    private val progressDao: ProgressDao
) : ViewModel() {
    fun downloads(): Flow<List<DownloadEntity>> = downloadsDao.listAll()
    fun progress(novelId: String): Flow<ProgressEntity?> = progressDao.observeProgress(novelId)
}
