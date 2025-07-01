package app.config

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
data class AppConfig(
    @Valid val kafka: KafkaProperties,
    @Valid val jwt: JWTProperties,
) {

    data class KafkaProperties(@NotEmpty val bootstrapServers: String,
                               @NotEmpty val group: String,
                               @Valid val topics: Topics
    ) {}

    data class Topics(@NotEmpty val email: String)

    data class JWTProperties(@NotEmpty val secret: String)
}