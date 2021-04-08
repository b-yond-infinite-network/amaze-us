package com.byond.challenge.delivery

import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.delivery.dto.response.UserDto
import com.byond.challenge.delivery.dto.response.UserListDto

interface UserController {

    fun createUser(userRequestDto: UserRequestDto): UserDto

    fun listUsers(limit: Int?, offset: Int?): UserListDto

    fun getUser(userId: Long): UserDto

    fun modifyUser(userId: Long, userRequestDto: UserRequestDto)
}