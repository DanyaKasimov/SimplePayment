package app

import app.config.AppConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties


@SpringBootApplication
@EnableConfigurationProperties(AppConfig::class)
open class NotificationApplication

fun main(args: Array<String>) {
    SpringApplication.run(NotificationApplication::class.java, *args)
}

