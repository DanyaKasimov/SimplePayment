package app.service.impl.notification

import app.clients.NotificationClient
import app.dto.EmailDTO
import app.service.NotificationService
import org.springframework.stereotype.Service

@Service
class NotificationHTTPService(private val notificationClient: NotificationClient) : NotificationService {

    override fun sendEmail(address: String, content: String) {
        val emailDTO = EmailDTO(address, content)
        notificationClient.sendEmail(emailDTO)
    }
}