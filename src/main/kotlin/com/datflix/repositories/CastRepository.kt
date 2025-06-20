package com.datflix.repositories

import com.datflix.models.Cast
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CastRepository : Repository<Cast, Int> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("cast")

    override suspend fun getAll(): List<Cast> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(Cast::class.java) }
    }

    override suspend fun getById(id: Int): Cast? = withContext(Dispatchers.IO) {
        val doc = collection.document(id.toString()).get().get()
        if (doc.exists()) doc.toObject(Cast::class.java) else null
    }

    override suspend fun create(cast: Cast): Cast = withContext(Dispatchers.IO) {
        collection.document(cast.id.toString()).set(cast).get()
        cast
    }

    override suspend fun update(cast: Cast): Boolean = withContext(Dispatchers.IO) {
        val docRef = collection.document(cast.id.toString())
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(cast).get()
        true
    }

    override suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        collection.document(id.toString()).delete().get()
        true
    }
}
