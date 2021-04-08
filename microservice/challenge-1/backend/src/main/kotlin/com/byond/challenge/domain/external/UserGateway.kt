package com.byond.challenge.domain.external

import com.byond.challenge.domain.entity.User

interface UserGateway {
    fun saveUser(user: User): User

    fun listUsers(): List<User>

    fun getUser(userId: Long): User?
}
