package com.datflix.services

import com.datflix.models.Movie
import com.datflix.repositories.MovieRepository

class MovieService(private val movieRepository: MovieRepository) {

    suspend fun getAllMovies(): List<Movie> = movieRepository.getAll()

    suspend fun getMovieById(id: Int): Movie? = movieRepository.getById(id)

    suspend fun createMovie(movie: Movie): Movie {
        require(movie.title.isNotBlank()) { "Movie title cannot be empty" }
        require(movieRepository.getById(movie.id) == null) { "Movie with this ID already exists" }
        return movieRepository.create(movie)
    }

    suspend fun updateMovie(movie: Movie): Boolean {
        val existing = movieRepository.getById(movie.id) ?: return false
        return movieRepository.update(movie)
    }

    suspend fun deleteMovie(id: Int): Boolean {
        val existing = movieRepository.getById(id) ?: return false
        return movieRepository.delete(id)
    }
}
