package com.novella.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novella.app.data.local.entities.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes WHERE novelId = :novelId")
    fun list(novelId: String): Flow<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(quote: QuoteEntity)

    @Delete
    suspend fun remove(quote: QuoteEntity)
}
