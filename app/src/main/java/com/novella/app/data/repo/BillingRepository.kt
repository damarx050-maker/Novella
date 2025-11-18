package com.novella.app.data.repo

import com.novella.app.data.local.dao.PurchasesDao
import com.novella.app.data.local.entities.PurchaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BillingRepository(
    private val purchasesDao: PurchasesDao
) {
    fun isSubscriptionActive(): Flow<Boolean> = purchasesDao.listAll().map { list ->
        list.any { it.type == "SUBSCRIPTION" && it.status == "ACTIVE" }
    }

    fun hasOwnership(productId: String): Flow<Boolean> = purchasesDao.get(productId).map { it?.status == "ACTIVE" }

    suspend fun persistPurchase(entity: PurchaseEntity) {
        purchasesDao.upsert(entity)
    }
}
