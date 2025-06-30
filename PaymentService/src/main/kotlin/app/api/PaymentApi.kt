package app.api

import app.dto.ApiErrorResponse
import app.dto.MessageDTO
import app.dto.PaymentDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/payment")
//@PreAuthorize("isAuthenticated()")
@Tag(name = "Платежи", description = "Методы для управления платежами.")
interface PaymentApi {

    @Operation(description = "Совершить платежи.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Платеж совершен.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Кошелек не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun payment(@RequestBody @Valid paymentDTO: PaymentDTO): MessageDTO
}