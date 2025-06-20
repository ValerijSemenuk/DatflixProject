package com.datflix.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    val id: Int,
    val email: String,
    val username: String,
    val passwordHash: String,  // Захешований пароль!
    val avatarUrl: String? = null,
    val watchlist: List<Int> = emptyList(),  // IDs фільмів
    @Contextual val registrationDate: LocalDateTime = LocalDateTime.now() // Анотація Contextual
)