package app.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RequestVerificationDTO(
    @param:JsonProperty("email")
    @field:Email
    @field:NotBlank
    val email: String
) {}

