package com.datflix.services

import com.datflix.models.WatchlistItem
import com.datflix.repositories.WatchlistItemRepository

class WatchlistItemService(private val watchlistRepository: WatchlistItemRepository) {

    suspend fun getAllWatchlistItems(): List<WatchlistItem> = watchlistRepository.getAll()

    suspend fun getWatchlistItemById(id: Int): WatchlistItem? = watchlistRepository.getById(id)

    suspend fun createWatchlistItem(item: WatchlistItem): WatchlistItem {
        return watchlistRepository.create(item)
    }

    suspend fun updateWatchlistItem(item: WatchlistItem): Boolean {
        val existing = watchlistRepository.getById(item.id) ?: return false
        return watchlistRepository.update(item)
    }

    suspend fun deleteWatchlistItem(id: Int): Boolean {
        val existing = watchlistRepository.getById(id) ?: return false
        return watchlistRepository.delete(id)
    }
}
