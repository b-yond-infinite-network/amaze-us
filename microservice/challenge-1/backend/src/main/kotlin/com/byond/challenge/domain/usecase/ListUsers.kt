package com.byond.challenge.domain.usecase

import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.external.UserGateway
import com.byond.challenge.util.applyPagination

class ListUsers(private val userGateway: UserGateway) {

    companion object {
        private const val defaultLimit = 10
        private const val defaultOffset = 0
    }

    operator fun invoke(limit: Int?, offset: Int?): List<User> {
        val limitValue = limit ?: defaultLimit
        val offsetValue = offset ?: defaultOffset
        return userGateway.listUsers()
            .applyPagination(limitValue, offsetValue)
    }
}
