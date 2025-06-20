package com.datflix.models

data class Movie(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var releaseYear: Int? = null,
    var durationMinutes: Int? = null,
    var posterUrl: String? = null,
    var trailerUrl: String? = null,
    var genres: List<String> = emptyList(),
    var cast: List<String> = emptyList(),
    var averageRating: Float = 0f,
    var isTrending: Boolean = false
)
