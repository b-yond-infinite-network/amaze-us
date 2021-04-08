package com.byond.challenge.domain.entity

import com.byond.challenge.infrastructure.repository.model.UserModel

data class User(
    val id: Long?,
    val name: String,
    val email: String,
    val description: String
) {
    fun toUserModel(): UserModel {
        return UserModel(
            id = id,
            name = name,
            email = email,
            description = description
        )
    }
}
