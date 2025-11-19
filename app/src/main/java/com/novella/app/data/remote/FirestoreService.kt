package com.novella.app.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.novella.app.data.local.entities.NovelEntity
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreService(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val collection = db.collection("Novels")

    fun novelsByCategory(category: String): Flow<List<NovelEntity>> = callbackFlow {
        val registration = collection.whereEqualTo("category", category)
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.documents?.mapNotNull { doc ->
                    doc.toNovel()
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    // One-shot fetch used for manual refresh/retry flows
    suspend fun fetchCategoryOnce(category: String): List<NovelEntity> {
        val query = collection.whereEqualTo("category", category)
        val snapshot = Tasks.await(query.get())
        return snapshot.documents.mapNotNull { it.toNovel() }
    }

    fun novelById(id: String): Flow<NovelEntity?> = callbackFlow {
        val registration = collection.document(id)
            .addSnapshotListener { snapshot, _ ->
                trySend(snapshot?.toNovel())
            }
        awaitClose { registration.remove() }
    }

    fun searchByPrefix(field: String = "title", q: String): Flow<List<NovelEntity>> = callbackFlow {
        if (q.isBlank()) { trySend(emptyList()); awaitClose { }; return@callbackFlow }
        val end = q + "\uf8ff"
        val registration = collection
            .orderBy(field)
            .startAt(q)
            .endAt(end)
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.documents?.mapNotNull { it.toNovel() } ?: emptyList()
                trySend(list)
            }
        awaitClose { registration.remove() }
    }
}

private fun com.google.firebase.firestore.DocumentSnapshot.toNovel(): NovelEntity? {
    val idValue = getString("id") ?: id
    val titleValue = getString("title") ?: "عنوان غير متوفر"
    // If title missing treat entire doc invalid (avoid placeholder pollution)
    if (titleValue.isBlank()) return null
    val authorValue = getString("author") ?: "مؤلف غير معروف"
    val descriptionValue = getString("description") ?: "لا يوجد وصف متاح"
    val coverUrlValue = getString("coverUrl") ?: ""
    val pdfUrlValue = getString("pdfUrl") ?: ""
    val categoryValue = getString("category") ?: ""
    val languageValue = getString("language") ?: "EN"
    val isPremiumValue = getBoolean("premium") ?: false
    return NovelEntity(
        id = idValue,
        title = titleValue,
        author = authorValue,
        language = languageValue,
        description = descriptionValue,
        coverUrl = coverUrlValue,
        pdfUrl = pdfUrlValue,
        category = categoryValue,
        isPremium = isPremiumValue
    )
}
