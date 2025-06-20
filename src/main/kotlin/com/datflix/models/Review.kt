package com.datflix.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Review(
    val id: Int,
    val movieId: Int,
    val userId: Int,
    val text: String,
    val rating: Int,  // 1-10
    @Contextual val createdAt: LocalDateTime = LocalDateTime.now() // Анотація Contextual
)