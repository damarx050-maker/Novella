package com.novella.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novella.app.data.local.dao.*
import com.novella.app.data.local.entities.*

@Database(
    entities = [
        NovelEntity::class,
        ProgressEntity::class,
        BookmarkEntity::class,
        QuoteEntity::class,
        DownloadEntity::class,
        PurchaseEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun novelsDao(): NovelsDao
    abstract fun progressDao(): ProgressDao
    abstract fun bookmarksDao(): BookmarksDao
    abstract fun quotesDao(): QuotesDao
    abstract fun downloadsDao(): DownloadsDao
    abstract fun purchasesDao(): PurchasesDao
}
