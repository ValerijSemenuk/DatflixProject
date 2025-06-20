package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val releaseYear: Int,
    val durationMinutes: Int,
    val posterUrl: String,
    val trailerUrl: String? = null,
    val genres: List<Int> = emptyList(),  // IDs жанрів
    val cast: List<Int> = emptyList(),    // IDs акторів
    val averageRating: Float = 0f,
    val isTrending: Boolean = false
)