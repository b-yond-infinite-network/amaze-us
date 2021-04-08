package com.byond.challenge.application.exceptionhandler

import com.byond.challenge.delivery.dto.ApiError
import com.byond.challenge.domain.exception.RequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RequestException::class)
    fun handleRequestException(ex: RequestException, request: WebRequest): ResponseEntity<ApiError> {
        val errorDetails = ApiError(ex.code, ex.message)
        logger.error("Exception thrown - CODE: ${ex.code}  MESSAGE: \"${ex.message}\" STATUS: ${ex.status}")
        return ResponseEntity(errorDetails, HttpStatus.valueOf(ex.status))
    }
}
