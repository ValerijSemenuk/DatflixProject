package com.datflix.repositories

interface Repository<T, ID> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: ID): T?
    suspend fun create(item: T): T
    suspend fun update(item: T): Boolean
    suspend fun delete(id: ID): Boolean
}
