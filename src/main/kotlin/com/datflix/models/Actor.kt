package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val photoUrl: String? = null,
    val bio: String? = null,
    val movies: List<Int> = emptyList()  // IDs фільмів
)