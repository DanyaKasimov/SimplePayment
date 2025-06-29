package app.service

interface NotificationService {

    fun sendEmail(address: String, content: String)
}