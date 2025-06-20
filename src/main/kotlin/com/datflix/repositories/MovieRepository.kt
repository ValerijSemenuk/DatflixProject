package com.datflix.repositories

import com.datflix.models.Movie
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository : Repository<Movie, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("movies")

    override suspend fun getAll(): List<Movie> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(Movie::class.java) }
    }

    override suspend fun getById(id: String): Movie? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(Movie::class.java) else null
    }

    override suspend fun create(movie: Movie): Movie = withContext(Dispatchers.IO) {
        val docRef = collection.document() // генерує унікальний ID Firestore
        val movieWithId = movie.copy(id = docRef.id)
        docRef.set(movieWithId).get()
        movieWithId
    }

    override suspend fun update(movie: Movie): Boolean = withContext(Dispatchers.IO) {
        val id = movie.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(movie).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}

