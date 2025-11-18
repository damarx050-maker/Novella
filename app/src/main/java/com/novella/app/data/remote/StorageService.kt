package com.novella.app.data.remote

import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import com.novella.app.utils.FileManager
import kotlinx.coroutines.tasks.await
import java.io.File

class StorageService(
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {
    suspend fun downloadPdfToLocal(context: Context, novelId: String, pdfUrl: String): File {
        val local = FileManager.novelPdfPath(context, novelId)
        // pdfUrl may be a gs:// or https URL
        val ref = if (pdfUrl.startsWith("gs://")) storage.getReferenceFromUrl(pdfUrl) else storage.getReferenceFromUrl(pdfUrl)
        ref.getFile(local).await()
        return local
    }
}
