package app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration

import java.net.URI

import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
open class S3Config {

    @Bean
    open fun s3Client(appConfig: AppConfig): S3Client {
        val credentials = AwsBasicCredentials
            .create("${appConfig.cloud.tenantId}:${appConfig.cloud.keyId}", appConfig.cloud.keySecret)

        return S3Client.builder()
            .region(Region.of("ru-central-1"))
            .endpointOverride(URI.create(appConfig.cloud.url))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .serviceConfiguration(
                S3Configuration.builder()
                    .pathStyleAccessEnabled(true)
                    .build()
            )
            .build()
    }

    @Bean
    open fun s3Presigner(appConfig: AppConfig): S3Presigner {
        val credentials = AwsBasicCredentials
            .create("${appConfig.cloud.tenantId}:${appConfig.cloud.keyId}", appConfig.cloud.keySecret)

        return S3Presigner.builder()
            .region(Region.of("ru-central-1"))
            .endpointOverride(URI.create(appConfig.cloud.url))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .serviceConfiguration(
                S3Configuration.builder()
                    .pathStyleAccessEnabled(true)
                    .build()
            )
            .build()
    }
}