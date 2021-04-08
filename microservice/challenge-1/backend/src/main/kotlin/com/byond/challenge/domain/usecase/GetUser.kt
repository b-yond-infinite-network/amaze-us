package com.byond.challenge.domain.usecase

import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.exception.RequestException
import com.byond.challenge.domain.external.UserGateway
import org.springframework.http.HttpStatus

class GetUser(private val userGateway: UserGateway) {

    operator fun invoke(userId: Long): User {
        return userGateway.getUser(userId) ?: throw RequestException("User not found", "not.found", HttpStatus.NOT_FOUND.value())
    }
}
