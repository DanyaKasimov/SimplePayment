package app.api

import app.dto.ApiErrorResponse
import app.dto.EmailDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestBody
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody


@Tag(name = "Notification", description = "Уведомления пользователей")
@RequestMapping("/notification")
interface NotificationAPI {

    @Operation(
        summary = "Отправить email",
        description = "Отправляет email-уведомление пользователю"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Email успешно отправлен"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ошибка валидации email или данных",
                content = [Content(schema = Schema(implementation = ApiErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = ApiErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/email")
    fun sendEmail(
        @SwaggerRequestBody(
            description = "Письмо для отправки",
            required = true,
            content = [Content(schema = Schema(implementation = EmailDTO::class))]
        )
        @RequestBody emailDTO: EmailDTO
    )
}