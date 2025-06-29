package app

import app.dto.EmailDTO
import app.service.NotificationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class NotificationListener(private val notificationService: NotificationService,
                           private val objectMapper: ObjectMapper) {

    @KafkaListener(
        topics = ["#{'\${app.kafka.topics.email}'}"],
        groupId = "#{'\${app.kafka.group}'}"
    )
    fun sendEmail(body: String) {
        val emailDTO = objectMapper.readValue(body, EmailDTO::class.java)
        notificationService.sendEmail(emailDTO);
    }
}