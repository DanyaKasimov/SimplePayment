package app.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class VerifyCodeDTO(
    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val code: String
) {}