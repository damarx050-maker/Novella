package com.novella.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey val productId: String,
    val type: String, // SUBSCRIPTION | ONE_TIME
    val status: String // ACTIVE | EXPIRED
)
