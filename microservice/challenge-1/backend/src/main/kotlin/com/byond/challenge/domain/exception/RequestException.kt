package com.byond.challenge.domain.exception

open class RequestException(
    override val message: String,
    val code: String,
    val status: Int
) : RuntimeException()
