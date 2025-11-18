package com.novella.app.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.novella.app.billing.BillingManager
import com.novella.app.data.local.AppDatabase
import com.novella.app.data.remote.FirestoreService
import com.novella.app.data.remote.StorageService
import com.novella.app.data.repo.BillingRepository
import com.novella.app.data.repo.NovelsRepository
import com.novella.app.data.repo.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideDb(app: Application): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, "novella.db").fallbackToDestructiveMigration().build()

    @Provides fun provideNovelsDao(db: AppDatabase) = db.novelsDao()
    @Provides fun provideProgressDao(db: AppDatabase) = db.progressDao()
    @Provides fun provideDownloadsDao(db: AppDatabase) = db.downloadsDao()
    @Provides fun providePurchasesDao(db: AppDatabase) = db.purchasesDao()
    @Provides fun provideBookmarksDao(db: AppDatabase) = db.bookmarksDao()
    @Provides fun provideQuotesDao(db: AppDatabase) = db.quotesDao()

    @Provides @Singleton fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides @Singleton fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides @Singleton fun provideStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides @Singleton fun provideFirestoreService(fs: FirebaseFirestore): FirestoreService = FirestoreService(fs)
    @Provides @Singleton fun provideStorageService(storage: FirebaseStorage): StorageService = StorageService(storage)

    @Provides @Singleton fun provideBillingRepository(db: AppDatabase): BillingRepository = BillingRepository(db.purchasesDao())

    @Provides @Singleton
    fun provideNovelsRepository(
        db: AppDatabase,
        remote: FirestoreService,
        billing: BillingRepository
    ): NovelsRepository = NovelsRepository(
        db.novelsDao(),
        db.progressDao(),
        db.downloadsDao(),
        db.bookmarksDao(),
        db.quotesDao(),
        remote,
        billing
    )

    @Provides @Singleton fun provideBillingManager(app: Application, billingRepository: BillingRepository): BillingManager = BillingManager(app, billingRepository)

    @Provides @Singleton
    fun provideSettingsRepository(context: Context): SettingsRepository = SettingsRepository(context)

    @Provides @Singleton
    fun provideIoScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}
