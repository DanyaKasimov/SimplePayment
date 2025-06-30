package app.dto

import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.Length

data class EmailDTO (

    @field:Email(message = "Невалидный email")
    val address: String,

    @field:Length(min = 1, max = 400)
    val content: String
) {
}