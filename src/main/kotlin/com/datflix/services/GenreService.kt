package com.datflix.services

import com.datflix.models.Genre
import com.datflix.repositories.GenreRepository

class GenreService(private val genreRepository: GenreRepository) {

    suspend fun getAllGenres(): List<Genre> = genreRepository.getAll()

    suspend fun getGenreById(id: Int): Genre? = genreRepository.getById(id)

    suspend fun createGenre(genre: Genre): Genre {
        require(genre.name.isNotBlank()) { "Genre name cannot be empty" }
        require(genreRepository.getById(genre.id) == null) { "Genre with this ID already exists" }
        return genreRepository.create(genre)
    }

    suspend fun updateGenre(genre: Genre): Boolean {
        val existing = genreRepository.getById(genre.id) ?: return false
        return genreRepository.update(genre)
    }

    suspend fun deleteGenre(id: Int): Boolean {
        val existing = genreRepository.getById(id) ?: return false
        return genreRepository.delete(id)
    }
}
