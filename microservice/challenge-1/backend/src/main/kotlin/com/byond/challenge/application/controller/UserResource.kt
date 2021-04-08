package com.byond.challenge.application.controller

import com.byond.challenge.delivery.UserController
import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.delivery.dto.response.UserDto
import com.byond.challenge.delivery.dto.response.UserListDto
import com.byond.challenge.domain.usecase.CreateUser
import com.byond.challenge.domain.usecase.GetUser
import com.byond.challenge.domain.usecase.ListUsers
import com.byond.challenge.domain.usecase.ModifyUser
import com.byond.challenge.util.toDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("users")
class UserResource(
    private val create: CreateUser,
    private val list: ListUsers,
    private val get: GetUser,
    private val modify: ModifyUser
) : UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun createUser(@RequestBody userRequestDto: UserRequestDto): UserDto {
        return create(userRequestDto).toDto()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun listUsers(limit: Int?, offset: Int?): UserListDto {
        return list(limit, offset).toDto()
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    override fun getUser(@PathVariable userId: Long): UserDto {
        return get(userId).toDto()
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun modifyUser(@PathVariable userId: Long, @RequestBody userRequestDto: UserRequestDto) {
        return modify(userId, userRequestDto)
    }
}
