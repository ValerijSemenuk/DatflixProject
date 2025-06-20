package com.datflix.services

import com.datflix.models.User
import com.datflix.repositories.UserRepository

class UserService(private val userRepository: UserRepository) {

    suspend fun getAllUsers(): List<User> = userRepository.getAll()

    suspend fun getUserById(id: Int): User? = userRepository.getById(id)

    suspend fun createUser(user: User): User {
        require(user.email.isNotBlank()) { "Email cannot be empty" }
        require(user.username.isNotBlank()) { "Username cannot be empty" }
        require(user.passwordHash.isNotBlank()) { "Password hash cannot be empty" }
        require(userRepository.getById(user.id) == null) { "User with this ID already exists" }
        return userRepository.create(user)
    }

    suspend fun updateUser(user: User): Boolean {
        val existing = userRepository.getById(user.id) ?: return false
        return userRepository.update(user)
    }

    suspend fun deleteUser(id: Int): Boolean {
        val existing = userRepository.getById(id) ?: return false
        return userRepository.delete(id)
    }
}
