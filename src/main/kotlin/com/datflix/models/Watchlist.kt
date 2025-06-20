package com.datflix.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class WatchlistItem(
    val id: Int,                // Унікальний ID запису
    val userId: Int,            // Посилання на користувача (User.id)
    val movieId: Int,           // Посилання на фільм (Movie.id)
    @Contextual val addedAt: LocalDateTime, // Дата додавання
    val isWatched: Boolean = false,
    val userRating: Int? = null // Оцінка користувача (1-10, опціонально)
) {
    fun isRecentlyAdded(days: Int = 7): Boolean {
        return addedAt.isAfter(LocalDateTime.now().minusDays(days.toLong()))
    }
}