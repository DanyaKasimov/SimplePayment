package app.dto

data class ApiErrorResponse(
    val description: String? = null,
    val code: String? = null,
    val exceptionName: String? = null,
    val exceptionMessage: String? = null
)