package com.datflix.models

data class Actor(
    var id: String? = null,
    var name: String? = null,
    var photoUrl: String? = null,
    var bio: String? = null,
    var movies: List<String> = emptyList()
)
