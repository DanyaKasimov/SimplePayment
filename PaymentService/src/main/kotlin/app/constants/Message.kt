package app.constants

object Message {

    fun paymentSender(name: String, surname: String, amount: Long, check: String) =
        "Здравствуйте $surname $name! Выполнен перевод $amount руб. Чек: $check"

    fun paymentRecipient(name: String, surname: String, amount: Long, check: String) =
        "Здравствуйте $surname $name! Зачислено $amount руб. Чек: $check"


}