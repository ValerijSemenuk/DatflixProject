package com.datflix.repositories

import com.datflix.models.Review
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepository : Repository<Review, Int> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("reviews")

    override suspend fun getAll(): List<Review> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(Review::class.java) }
    }

    override suspend fun getById(id: Int): Review? = withContext(Dispatchers.IO) {
        val doc = collection.document(id.toString()).get().get()
        if (doc.exists()) doc.toObject(Review::class.java) else null
    }

    override suspend fun create(review: Review): Review = withContext(Dispatchers.IO) {
        collection.document(review.id.toString()).set(review).get()
        review
    }

    override suspend fun update(review: Review): Boolean = withContext(Dispatchers.IO) {
        val docRef = collection.document(review.id.toString())
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(review).get()
        true
    }

    override suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        collection.document(id.toString()).delete().get()
        true
    }
}
