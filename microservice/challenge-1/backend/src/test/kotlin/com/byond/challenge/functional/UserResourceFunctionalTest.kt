package com.byond.challenge.functional

import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.delivery.dto.response.UserDto
import com.byond.challenge.delivery.dto.response.UserListDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus


class UserResourceFunctionalTest : FunctionalTest() {

    @Test
    fun `test create user`() {
        // Given
        val userRequestDto = UserRequestDto(null, "Santi", "santi@santi.com", "Description")
        val entity = HttpEntity(userRequestDto)

        // When
        val createdUser = testRestTemplate.exchange(
            controller,
            HttpMethod.POST,
            entity,
            UserDto::class.java
        )

        // Then
        assertNotNull(createdUser)
        assertEquals(HttpStatus.CREATED, createdUser.statusCode)
    }

    @Test
    fun `test list users`() {
        // Given
        `test create user`()

        // When
        val results = testRestTemplate.exchange(
            controller,
            HttpMethod.GET,
            null,
            UserListDto::class.java
        )

        // Then
        assertNotNull(results)
        assertEquals(HttpStatus.OK, results.statusCode)
        assertEquals(1, results.body?.users?.size)
    }

    @Test
    fun `test get user`() {
        // Given
        `test create user`()
        val getUserUrl = "$controller/1"

        // When
        val user = testRestTemplate.exchange(
            getUserUrl,
            HttpMethod.GET,
            null,
            UserDto::class.java
        )

        // Then
        assertNotNull(user)
        assertEquals(HttpStatus.OK, user.statusCode)
    }

    @Test
    fun `test modify user`() {
        // Given
        `test create user`()
        val modifyUserUrl = "$controller/1"
        val userUpdateDto = UserRequestDto(1L, "Santiago", "santiago@santiago.com", "Description")
        val entity = HttpEntity(userUpdateDto)

        // When
        val response = testRestTemplate.exchange(
            modifyUserUrl,
            HttpMethod.PUT,
            entity,
            Any::class.java
        )

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}
