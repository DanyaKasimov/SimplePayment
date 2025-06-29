package app.api

import app.dto.*
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
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RequestMapping("/auth")
@PreAuthorize("isAnonymous()")
@Tag(name = "Авторизация", description = "Методы для авторизации.")
interface AuthApi {

    @Operation(description = "Отправить код подтверждения на почту.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Код отправлен на почту.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Электронная почта уже существует.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/request-verification")
    @ResponseStatus(HttpStatus.OK)
    fun requestVerification(
        @SwaggerRequestBody(
            description = "Почта пользователя",
                required = true,
                content = [Content(schema = Schema(implementation = RequestVerificationDTO::class))]
        )
        @RequestBody @Valid verificationDTO: RequestVerificationDTO): MessageDTO

    @Operation(description = "Подтверждение кода.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Код подтвержден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный код.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/verify-code")
    @ResponseStatus(HttpStatus.OK)
    fun verifyCode(
        @SwaggerRequestBody(
            description = "Код верификации",
            required = true,
            content = [Content(schema = Schema(implementation = VerifyCodeDTO::class))]
        )
        @RequestBody @Valid verifyCodeDTO: VerifyCodeDTO): MessageDTO

    @Operation(description = "Регистрация пользователя.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Регистрация прошла успешно.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Пользователь уже существует.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Почта не подтверждена.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    fun signup(
        @SwaggerRequestBody(
            description = "Регистрация пользователя",
            required = true,
            content = [Content(schema = Schema(implementation = SignUpDTO::class))]
        )
        @RequestBody @Valid signUpDTO: SignUpDTO) : MessageDTO

    @Operation(description = "Вход пользователя в аккаунт.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Вход прошел успешно.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Неверные данные пользователя.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
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
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signin(
        @SwaggerRequestBody(
            description = "Вход пользователя",
            required = true,
            content = [Content(schema = Schema(implementation = SignInDTO::class))]
        )
        @RequestBody @Valid signInDTO: SignInDTO) : MessageDTO
}