package app.service.impl.notification

import app.service.NotificationService
import org.springframework.stereotype.Service

@Service
class NotificationDelegatorService(private val primary: NotificationService,
                                   private val secondary: NotificationService) : NotificationService {

    override fun sendEmail(address: String, content: String, token: String) {
        try {
            primary.sendEmail(address, content, token)
        } catch (e: Exception) {
            try {
                secondary.sendEmail(address, content, token)
            } catch (ex: Exception) {
                throw RuntimeException(
                    "Не удалось отправить уведомление ни через один транспорт. Message: ${ex.message}"
                )
            }
        }
    }
}