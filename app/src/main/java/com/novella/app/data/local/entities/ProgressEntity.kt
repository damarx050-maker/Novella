package com.novella.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey val novelId: String,
    val currentPage: Int,
    val totalPages: Int,
    val percent: Int
)
