package com.byond.challenge.infrastructure.gateway

import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.external.UserGateway
import com.byond.challenge.infrastructure.repository.UserRepository

class UserGatewayImpl(private val userRepository: UserRepository): UserGateway {

    override fun saveUser(user: User): User {
        return userRepository.saveUser(user)
    }

    override fun listUsers(): List<User> {
        return userRepository.listUsers()
    }

    override fun getUser(userId: Long): User? {
        return userRepository.getUser(userId)
    }
}
