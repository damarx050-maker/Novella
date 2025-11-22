package com.novella.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novella.app.data.local.entities.NovelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NovelsDao {
    @Query("SELECT * FROM novels WHERE category = :category")
    fun getByCategory(category: String): Flow<List<NovelEntity>>

    @Query("SELECT * FROM novels WHERE id = :id LIMIT 1")
    fun getById(id: String): Flow<NovelEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<NovelEntity>)

    @Query("DELETE FROM novels WHERE category IN (:obsolete)")
    suspend fun deleteByCategories(obsolete: List<String>)

    @Query("DELETE FROM novels WHERE category NOT IN (:allowed)")
    suspend fun pruneToAllowed(allowed: List<String>)
}
