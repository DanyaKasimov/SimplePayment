package app.config

import app.constants.TransportEnum
import app.service.impl.notification.NotificationDelegatorService
import app.service.impl.notification.NotificationHTTPService
import app.service.impl.notification.NotificationKAFKAService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
open class TransportConfig(private val appConfig: AppConfig) {

    @Bean
    @Primary
    open fun notificationService(httpService: NotificationHTTPService,
                                 kafkaService: NotificationKAFKAService
    ): NotificationDelegatorService {
        return when (appConfig.transport.type) {
            TransportEnum.HTTP -> NotificationDelegatorService(httpService, kafkaService)
            TransportEnum.KAFKA -> NotificationDelegatorService(kafkaService, httpService)
        }
    }
}