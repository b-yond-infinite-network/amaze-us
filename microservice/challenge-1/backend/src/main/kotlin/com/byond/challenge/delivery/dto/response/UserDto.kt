package com.byond.challenge.delivery.dto.response

import com.byond.challenge.domain.entity.User

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val description: String
) {
    @Suppress("unused")
    constructor(user: User) : this(
        id = user.id!!,
        name = user.name,
        email = user.email,
        description = user.description
    )
}
