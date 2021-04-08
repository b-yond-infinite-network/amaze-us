package com.byond.challenge.domain.usecase

import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.domain.exception.RequestException
import com.byond.challenge.domain.external.UserGateway
import org.springframework.http.HttpStatus

class ModifyUser(private val userGateway: UserGateway) {

    operator fun invoke(userId: Long, userRequestDto: UserRequestDto) {
        userGateway.getUser(userId)
            ?.let {
                val modifiedUser = it.copy(
                    id = it.id,
                    name = userRequestDto.name,
                    email = userRequestDto.email,
                    description = userRequestDto.description
                )
                userGateway.saveUser(modifiedUser)
            } ?: throw RequestException("User not found", "not.found", HttpStatus.NOT_FOUND.value())
    }
}
