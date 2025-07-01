package app.service

import app.dto.EmailDTO
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class NotificationService(private val emailSender: JavaMailSender) {

    fun sendEmail(body: EmailDTO) {
        val message = SimpleMailMessage()
        message.setTo(body.address)
        message.subject = "SimplePayment"
        message.text = body.content
        emailSender.send(message)
    }
}