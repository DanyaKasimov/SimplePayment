package app.constants

object Message {

    fun signupUser(name: String, surname: String) =
        "Здравствуйте $surname $name! Вы успешно зарегистрировались в SimplePayment"


    fun code(code: String) =
        "Ваш код подтверждения: $code"
}