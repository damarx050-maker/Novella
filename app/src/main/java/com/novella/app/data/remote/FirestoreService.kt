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
    val id = getString("id") ?: id
    val title = getString("title") ?: return null
    val author = getString("author") ?: ""
    val description = getString("description") ?: ""
    val coverUrl = getString("coverUrl") ?: ""
    val pdfUrl = getString("pdfUrl") ?: ""
    val category = getString("category") ?: ""
    val language = getString("language") ?: "EN"
    return NovelEntity(id, title, author, language, description, coverUrl, pdfUrl, category)
}
