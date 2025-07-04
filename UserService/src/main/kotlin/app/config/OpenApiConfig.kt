package app.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.security.SecurityRequirement as OpenApiSecurityRequirement
@Configuration
@OpenAPIDefinition(
    info = Info(title = "API Documentation", version = "v1"),
    security = [SecurityRequirement(name = "bearerAuth")]
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
open class OpenApiConfig {

    @Bean
    open fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(OpenApiSecurityRequirement().addList("bearerAuth"))
    }
}