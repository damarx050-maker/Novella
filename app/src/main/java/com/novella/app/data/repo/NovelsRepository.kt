package com.novella.app.data.repo

import com.novella.app.data.local.dao.DownloadsDao
import com.novella.app.data.local.dao.BookmarksDao
import com.novella.app.data.local.dao.QuotesDao
import com.novella.app.data.local.dao.NovelsDao
import com.novella.app.data.local.dao.ProgressDao
import com.novella.app.data.local.entities.DownloadEntity
import com.novella.app.data.local.entities.NovelEntity
import com.novella.app.data.local.entities.ProgressEntity
import com.novella.app.data.local.entities.BookmarkEntity
import com.novella.app.data.local.entities.QuoteEntity
import com.novella.app.data.remote.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NovelsRepository(
    private val novelsDao: NovelsDao,
    private val progressDao: ProgressDao,
    private val downloadsDao: DownloadsDao,
    private val bookmarksDao: BookmarksDao,
    private val quotesDao: QuotesDao,
    private val remote: FirestoreService,
    private val billingRepository: BillingRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private val startedCategories = mutableSetOf<String>()
    fun novelsByCategory(category: String): Flow<List<NovelEntity>> {
        // Sync remote -> local, ensure single listener per category
        if (startedCategories.add(category)) {
            scope.launch {
                remote.novelsByCategory(category).collect { list ->
                    novelsDao.upsertAll(list)
                }
            }
        }
        return novelsDao.getByCategory(category)
    }

    fun novel(id: String): Flow<NovelEntity?> = novelsDao.getById(id)

    fun progress(novelId: String): Flow<ProgressEntity?> = progressDao.observeProgress(novelId)

    suspend fun updateProgress(novelId: String, current: Int, total: Int) {
        val percent = if (total > 0) (current * 100 / total) else 0
        progressDao.upsert(ProgressEntity(novelId, current, total, percent))
    }

    fun canAccess(novel: NovelEntity): Flow<Boolean> =
        if (!novel.isPremium) {
            kotlinx.coroutines.flow.flowOf(true)
        } else {
            billingRepository.isSubscriptionActive()
                .flatMapLatest { active ->
                    kotlinx.coroutines.flow.flowOf(active)
                }
                .flowOn(Dispatchers.IO)
        }

    suspend fun markDownloaded(novel: NovelEntity, localPath: String) {
        downloadsDao.upsert(DownloadEntity(novel.id, true, localPath))
    }

    suspend fun addBookmark(novelId: String, page: Int, note: String?) {
        bookmarksDao.add(BookmarkEntity(id = "$novelId-$page", novelId = novelId, page = page, note = note))
    }

    suspend fun addQuote(novelId: String, page: Int, text: String) {
        quotesDao.add(QuoteEntity(id = "$novelId-${'$'}page-${'$'}{text.hashCode()}", novelId = novelId, page = page, text = text))
    }

    // Manual refresh used by retry UX; does one-shot fetch for allowed categories only
    suspend fun refreshAll(): Result<Unit> = try {
        val categories = listOf(
            "NEW",
            "POPULAR",
            "PHILOSOPHY",
            "HORROR",
            "ROMANCE",
            "HISTORICAL",
            "FANTASY"
        )
        for (c in categories) {
            val list = remote.fetchCategoryOnce(c)
            novelsDao.upsertAll(list.filter { it.category in categories })
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Manual refresh for a single category; ignores removed/unsupported categories
    suspend fun refreshCategory(category: String): Result<Unit> = try {
        val allowed = setOf(
            "NEW", "POPULAR", "PHILOSOPHY", "HORROR", "ROMANCE", "HISTORICAL", "FANTASY"
        )
        if (category !in allowed) return Result.success(Unit)
        val list = remote.fetchCategoryOnce(category).filter { it.category in allowed }
        novelsDao.upsertAll(list)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
