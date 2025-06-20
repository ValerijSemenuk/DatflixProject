package com.datflix.repositories

import com.datflix.models.MovieCast
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Для MovieCast можна зробити id з composite key (movieId_actorId) або просто генерувати свій Int id.

class MovieCastRepository : Repository<MovieCast, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("movie_cast")

    override suspend fun getAll(): List<MovieCast> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(MovieCast::class.java) }
    }

    override suspend fun getById(id: String): MovieCast? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(MovieCast::class.java) else null
    }

    override suspend fun create(movieCast: MovieCast): MovieCast = withContext(Dispatchers.IO) {
        // Ідентифікатор документа, наприклад: "${movieCast.movieId}_${movieCast.actorId}"
        val docId = "${movieCast.movieId}_${movieCast.actorId}"
        collection.document(docId).set(movieCast).get()
        movieCast
    }

    override suspend fun update(movieCast: MovieCast): Boolean = withContext(Dispatchers.IO) {
        val docId = "${movieCast.movieId}_${movieCast.actorId}"
        val docRef = collection.document(docId)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(movieCast).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}
