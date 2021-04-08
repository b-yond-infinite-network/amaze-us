package com.byond.challenge.infrastructure.repository

import com.byond.challenge.domain.entity.User

interface UserRepository {
    fun saveUser(user: User): User

    fun listUsers(): List<User>

    fun getUser(userId: Long): User?
}
