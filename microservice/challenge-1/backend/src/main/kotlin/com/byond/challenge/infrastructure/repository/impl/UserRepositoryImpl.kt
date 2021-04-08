package com.byond.challenge.infrastructure.repository.impl

import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.exception.RequestException
import com.byond.challenge.infrastructure.repository.Repository
import com.byond.challenge.infrastructure.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull

class UserRepositoryImpl(private val repository: Repository) : UserRepository {

    override fun saveUser(user: User): User {
        return repository.save(user.toUserModel())
            .toUser()
    }

    override fun listUsers(): List<User> {
        return repository.findAll()
            .map { it.toUser() }
    }

    override fun getUser(userId: Long): User? {
        return repository.findByIdOrNull(userId)
            ?.toUser()
    }
}
