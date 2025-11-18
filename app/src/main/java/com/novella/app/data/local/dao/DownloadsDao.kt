package com.novella.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novella.app.data.local.entities.DownloadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadsDao {
    @Query("SELECT * FROM downloads")
    fun listAll(): Flow<List<DownloadEntity>>

    @Query("SELECT * FROM downloads WHERE novelId = :novelId")
    fun get(novelId: String): Flow<DownloadEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: DownloadEntity)

    @Query("DELETE FROM downloads WHERE novelId = :novelId")
    suspend fun delete(novelId: String)
}
