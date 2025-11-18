package com.novella.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val novelId: String,
    val isDownloaded: Boolean,
    val localPath: String
)
