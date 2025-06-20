package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String,
    val iconUrl: String? = null  // URL іконки жанру
)