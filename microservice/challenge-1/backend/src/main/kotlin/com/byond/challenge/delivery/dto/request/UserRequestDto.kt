package com.byond.challenge.delivery.dto.request

import com.byond.challenge.domain.entity.User

data class UserRequestDto(
    val id: Long?,
    val name: String,
    val email: String,
    val description: String
) {
    fun toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            description = description
        )
    }
}
