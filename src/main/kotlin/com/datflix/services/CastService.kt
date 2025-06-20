package com.datflix.services

import com.datflix.models.Cast
import com.datflix.repositories.CastRepository

class CastService(private val castRepository: CastRepository) {

    suspend fun getAllCasts(): List<Cast> = castRepository.getAll()

    suspend fun getCastById(id: Int): Cast? = castRepository.getById(id)

    suspend fun createCast(cast: Cast): Cast {
        require(cast.characterName.isNotBlank()) { "Character name cannot be empty" }
        require(castRepository.getById(cast.id) == null) { "Cast with this ID already exists" }
        return castRepository.create(cast)
    }

    suspend fun updateCast(cast: Cast): Boolean {
        val existing = castRepository.getById(cast.id) ?: return false
        return castRepository.update(cast)
    }

    suspend fun deleteCast(id: Int): Boolean {
        val existing = castRepository.getById(id) ?: return false
        return castRepository.delete(id)
    }
}
