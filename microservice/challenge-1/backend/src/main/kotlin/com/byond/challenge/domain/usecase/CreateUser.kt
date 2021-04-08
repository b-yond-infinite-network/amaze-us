package com.byond.challenge.domain.usecase

import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.external.UserGateway

class CreateUser(private val userGateway: UserGateway) {

    operator fun invoke(userRequestDto: UserRequestDto): User {
        return userGateway.saveUser(userRequestDto.toUser())
    }
}
