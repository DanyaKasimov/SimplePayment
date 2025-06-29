package app.dto


data class EmailDTO (

//    @field:Email(message = "Невалидный email")
    val address: String,

//    @field:Length(min = 1, max = 400)
    val content: String
) {
}