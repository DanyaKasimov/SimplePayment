package app.service.impl.notification

import app.config.AppConfig
import app.dto.EmailDTO
import app.service.NotificationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationKAFKAService(private val kafkaTemplateString: KafkaTemplate<String, String>,
                               private val objectMapper: ObjectMapper,
                               private val appConfig: AppConfig) : NotificationService {

    override fun sendEmail(address: String, content: String, token: String) {
        val emailDTO = EmailDTO(address, content)
        val emailBody = objectMapper.writeValueAsString(emailDTO)

        kafkaTemplateString.send(appConfig.kafka.topics.email, emailBody)
    }
}