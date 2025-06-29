package app.controller

import app.api.NotificationAPI
import app.dto.EmailDTO
import app.service.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(private val notificationService: NotificationService) : NotificationAPI {

    private val log = LoggerFactory.getLogger(NotificationController::class.java)

    override fun sendEmail(emailDTO: EmailDTO) {
        log.info("Поступил запрос по HTTP на отправку уведомления")
        notificationService.sendEmail(emailDTO)
    }

}