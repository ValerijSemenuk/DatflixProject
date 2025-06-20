package com.datflix.repositories

import com.datflix.models.User
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository : Repository<User, Int> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("users")

    override suspend fun getAll(): List<User> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(User::class.java) }
    }

    override suspend fun getById(id: Int): User? = withContext(Dispatchers.IO) {
        val doc = collection.document(id.toString()).get().get()
        if (doc.exists()) doc.toObject(User::class.java) else null
    }

    override suspend fun create(user: User): User = withContext(Dispatchers.IO) {
        collection.document(user.id.toString()).set(user).get()
        user
    }

    override suspend fun update(user: User): Boolean = withContext(Dispatchers.IO) {
        val docRef = collection.document(user.id.toString())
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(user).get()
        true
    }

    override suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        collection.document(id.toString()).delete().get()
        true
    }
}
