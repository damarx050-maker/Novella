package com.novella.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novels")
data class NovelEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val language: String,
    val description: String,
    val coverUrl: String,
    val pdfUrl: String,
    val category: String,
    val isPremium: Boolean = false
)
