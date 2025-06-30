package app.controller

import app.dto.ApiErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandlerController {

    private val log = LoggerFactory.getLogger(ExceptionHandlerController::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        val errors = ex.bindingResult
            .allErrors
            .mapNotNull { it.defaultMessage }

        log.warn("Ошибка валидации данных: {}", errors)
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Ошибка валидации данных: $errors", ex)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeExceptions(ex: RuntimeException): ResponseEntity<ApiErrorResponse> {
        val status = resolveResponseStatus(ex)
        log.warn("Обработка исключения. Type: {}. Message: {}", ex.javaClass.simpleName, ex.message)
        return buildErrorResponse(status, ex.message ?: "Неизвестная ошибка", ex)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(ex: Exception): ResponseEntity<ApiErrorResponse> {
        log.error("Непредвиденная ошибка", ex)
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка", ex)
    }

    private fun buildErrorResponse(
        status: HttpStatus,
        description: String,
        ex: Exception
    ): ResponseEntity<ApiErrorResponse> {
        val error = ApiErrorResponse(
            description = description,
            code = status.reasonPhrase,
            exceptionName = ex.javaClass.simpleName,
            exceptionMessage = ex.message
        )

        return ResponseEntity.status(status).body(error)
    }

    private fun resolveResponseStatus(ex: Throwable): HttpStatus {
        val annotation = ex.javaClass.getAnnotation(ResponseStatus::class.java)
        return annotation?.value ?: HttpStatus.INTERNAL_SERVER_ERROR
    }
}