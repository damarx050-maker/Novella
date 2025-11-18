package com.novella.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val id: String,
    val novelId: String,
    val page: Int,
    val text: String
)
