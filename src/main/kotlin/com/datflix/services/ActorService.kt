package com.datflix.services

import com.datflix.models.Actor
import com.datflix.repositories.ActorRepository

class ActorService(private val actorRepository: ActorRepository) {

    suspend fun getAllActors(): List<Actor> = actorRepository.getAll()

    suspend fun getActorById(id: Int): Actor? = actorRepository.getById(id)

    suspend fun createActor(actor: Actor): Actor {
        require(actor.name.isNotBlank()) { "Actor name cannot be empty" }
        require(actorRepository.getById(actor.id) == null) { "Actor with this ID already exists" }
        return actorRepository.create(actor)
    }

    suspend fun updateActor(actor: Actor): Boolean {
        val existing = actorRepository.getById(actor.id) ?: return false
        return actorRepository.update(actor)
    }

    suspend fun deleteActor(id: Int): Boolean {
        val existing = actorRepository.getById(id) ?: return false
        return actorRepository.delete(id)
    }
}
