package app.clients

import app.dto.EmailDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PutMapping

@Component
@FeignClient("notification-service", url = "\${app.notification-service.url}/notification")
interface NotificationClient {

    @PutMapping("/email")
    fun sendEmail(emailDTO: EmailDTO)
}