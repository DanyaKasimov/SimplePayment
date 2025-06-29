package app.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignInDTO(
    @field:Email(message = "Неверный формат email")
    @field:NotBlank(message = "Email обязателен")
    val email: String,

    @field:NotBlank(message = "Пароль обязателен")
    @field:Size(min = 6, max = 32, message = "Длина пароля должна быть от 6 до 32 символов")
    val password: String,
) {
}