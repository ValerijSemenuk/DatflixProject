package com.datflix.models

import java.time.LocalDateTime

data class User(
    var id: String? = null,
    var email: String? = null,
    var username: String? = null,
    var passwordHash: String? = null,
    var avatarUrl: String? = null,
    var watchlist: List<String> = emptyList(),
    var registrationDate: LocalDateTime? = null
)
