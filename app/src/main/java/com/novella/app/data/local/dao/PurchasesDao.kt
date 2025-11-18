package com.novella.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.novella.app.data.local.entities.PurchaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchasesDao {
    @Query("SELECT * FROM purchases")
    fun listAll(): Flow<List<PurchaseEntity>>

    @Query("SELECT * FROM purchases WHERE productId = :productId")
    fun get(productId: String): Flow<PurchaseEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PurchaseEntity)
}
