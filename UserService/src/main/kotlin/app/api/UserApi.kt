package app.api

import app.dto.ApiErrorResponse
import app.dto.ExistDTO
import app.dto.MessageDTO
import app.dto.UserDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Пользователи", description = "Методы для управления пользователями.")
interface UserApi {

    @Operation(description = "Проверка на существование пользователя.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь существует.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExistDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun existUser(@PathVariable("userId") @Valid userId: UUID): ExistDTO


    @Operation(description = "Получение пользователя.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь получен.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExistDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(@PathVariable("userId") @Valid userId: UUID): UserDTO
}