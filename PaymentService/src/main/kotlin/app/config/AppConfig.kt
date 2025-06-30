package app.config

import app.constants.TransportEnum
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
data class AppConfig(
    @Valid val kafka: KafkaProperties,
    @Valid val transport: TransportProperties,
    @Valid val notificationService: NotificationServiceProperties,
    @Valid val userService: UserServiceProperties,
    @Valid val redis: RedisProperties,
    @Valid val jwt: JWTProperties,
    @Valid val cloud: CloudProperties,
) {

    data class KafkaProperties(@NotEmpty val bootstrapServers: String,
                               @NotEmpty val group: String,
                               @Valid val topics: Topics
    ) {}

    data class Topics(@NotEmpty val payment: String,
                      @NotEmpty val email: String)

    data class TransportProperties(@NotEmpty val type: TransportEnum)

    data class RedisProperties(@NotEmpty val prefix: String)

    data class JWTProperties(@NotEmpty val secret: String)

    data class CloudProperties(@NotEmpty val keyId: String,
                               @NotEmpty val keySecret: String,
                               @NotEmpty val url: String,
                               @NotEmpty val tenantId: String,
                               @NotEmpty val bucket: String)


    data class NotificationServiceProperties(@NotEmpty val url: String)

    data class UserServiceProperties(@NotEmpty val url: String)
}