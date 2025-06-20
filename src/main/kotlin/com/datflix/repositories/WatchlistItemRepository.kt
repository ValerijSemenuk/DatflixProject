package com.datflix.repositories

import com.datflix.models.WatchlistItem
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WatchlistItemRepository : Repository<WatchlistItem, Int> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("watchlist_items")

    override suspend fun getAll(): List<WatchlistItem> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(WatchlistItem::class.java) }
    }

    override suspend fun getById(id: Int): WatchlistItem? = withContext(Dispatchers.IO) {
        val doc = collection.document(id.toString()).get().get()
        if (doc.exists()) doc.toObject(WatchlistItem::class.java) else null
    }

    override suspend fun create(item: WatchlistItem): WatchlistItem = withContext(Dispatchers.IO) {
        collection.document(item.id.toString()).set(item).get()
        item
    }

    override suspend fun update(item: WatchlistItem): Boolean = withContext(Dispatchers.IO) {
        val docRef = collection.document(item.id.toString())
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(item).get()
        true
    }

    override suspend fun delete(id: Int): Boolean = withContext(Dispatchers.IO) {
        collection.document(id.toString()).delete().get()
        true
    }
}
