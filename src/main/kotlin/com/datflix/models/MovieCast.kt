package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieCast(
    val movieId: Int,
    val actorId: Int,
    val characterName: String,  // Наприклад: "Тоні Старк / Залізна людина"
    val isMainRole: Boolean = true
)