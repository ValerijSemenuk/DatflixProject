package com.datflix.repositories

import com.datflix.models.Actor
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActorRepository : Repository<Actor, Int> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("actors")

    override suspend fun getAll(): List<Actor> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(Actor::class.java) }
    }

    override suspend fun getById(id: Int): Actor? = withContext(Dispatchers.IO) {
        val doc = collection.document(id.toString()).get().get()
        if (doc.exists()) doc.toObject(Actor::class.java) else null
    }

    override suspend fun create(actor: Actor): Actor = withContext(Dispatchers.IO) {
        collection.document(actor.id.toString()).set(actor).get()
        actor
    }

    override suspend fun update(actor: Actor): Boolean = withContext(Dispatchers.IO) {
        val docRef = collection.document(actor.id.toString())
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(actor).get()
        true
    }

    override suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        collection.document(id.toString()).delete().get()
        true
    }
}
