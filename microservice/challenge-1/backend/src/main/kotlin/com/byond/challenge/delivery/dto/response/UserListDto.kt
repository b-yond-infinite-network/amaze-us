package com.byond.challenge.delivery.dto.response

import com.byond.challenge.domain.entity.User
import com.byond.challenge.util.toDto

data class UserListDto(
    val users: Collection<UserDto>
) {
    @Suppress("unused")
    constructor(users: List<User>) : this(
        users = users.map { it.toDto<User, UserDto>() }
    )
}
