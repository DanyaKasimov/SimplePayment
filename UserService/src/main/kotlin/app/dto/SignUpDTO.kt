package app.dto

import app.constants.Gender
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class SignUpDTO(
    @Schema(
        description = "Имя пользователя",
        example = "Иван",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Имя не может быть пустым")
    val name: String,

    @Schema(
        description = "Фамилия пользователя",
        example = "Иванов",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Фамилия не может быть пустой")
    val surname: String,

    @Schema(
        description = "Отчество пользователя (необязательно)",
        example = "Иванович",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val middle: String? = null,

    @Schema(
        description = "Дата рождения",
        example = "1990-01-01",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotNull
    val birthday: LocalDate,

    @Schema(
        description = "Пол пользователя",
        implementation = Gender::class,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotNull
    val gender: Gender,

    @Schema(
        description = "ИНН пользователя",
        example = "123456789012",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "ИНН обязателен")
    val inn: String,

    @Schema(
        description = "Адрес проживания",
        example = "г. Москва, ул. Ленина, д. 10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Адрес обязателен")
    val address: String,

    @Schema(
        description = "Email пользователя",
        example = "user@example.com",
        format = "email",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:Email(message = "Неверный формат email")
    @field:NotBlank(message = "Email обязателен")
    val email: String,

    @Schema(
        description = "Пароль (6-32 символа)",
        example = "securePassword123",
        minLength = 6,
        maxLength = 32,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Пароль обязателен")
    @field:Size(min = 6, max = 32, message = "Длина пароля должна быть от 6 до 32 символов")
    val password: String,

    @Schema(
        description = "Серия паспорта",
        example = "4510",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Серия обязательна")
    val passportSeria: String,

    @Schema(
        description = "Номер паспорта",
        example = "123456",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Номер обязателен")
    val passportNumber: String,
)