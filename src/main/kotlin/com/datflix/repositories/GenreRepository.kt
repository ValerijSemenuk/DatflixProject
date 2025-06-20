package com.datflix.repositories

import com.datflix.models.Genre
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepository : Repository<Genre, Int> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("genres")

    override suspend fun getAll(): List<Genre> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(Genre::class.java) }
    }

    override suspend fun getById(id: Int): Genre? = withContext(Dispatchers.IO) {
        val doc = collection.document(id.toString()).get().get()
        if (doc.exists()) doc.toObject(Genre::class.java) else null
    }

    override suspend fun create(genre: Genre): Genre = withContext(Dispatchers.IO) {
        collection.document(genre.id.toString()).set(genre).get()
        genre
    }

    override suspend fun update(genre: Genre): Boolean = withContext(Dispatchers.IO) {
        val docRef = collection.document(genre.id.toString())
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(genre).get()
        true
    }

    override suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        collection.document(id.toString()).delete().get()
        true
    }
}
