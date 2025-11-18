package com.novella.app.utils

import android.content.Context
import java.io.File

object FileManager {
    fun novelsDir(context: Context): File = File(context.filesDir, "novels").apply { mkdirs() }

    fun novelPdfPath(context: Context, novelId: String): File = File(novelsDir(context), "$novelId.pdf")

    fun isNovelDownloaded(context: Context, novelId: String): Boolean = novelPdfPath(context, novelId).exists()

    fun deleteNovelFile(context: Context, novelId: String): Boolean = novelPdfPath(context, novelId).delete()
}
