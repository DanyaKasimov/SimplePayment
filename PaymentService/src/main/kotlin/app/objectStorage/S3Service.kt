package app.objectStorage

import app.config.AppConfig
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.io.File
import java.nio.file.Files
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class S3Service(
    private val s3: S3Client,
    private val presigner: S3Presigner,
    private val config: AppConfig
) {

    fun upload(file: File, originalFileName: String? = null, contentType: String = "application/octet-stream"): String {
        val key = originalFileName ?: "upload-${LocalDateTime.now()}"
        val putRequest = PutObjectRequest.builder()
            .bucket(config.cloud.bucket)
            .key(key)
            .contentType(contentType)
            .build()

        s3.putObject(putRequest, file.toPath())
        return key
    }

    fun generateUrl(key: String): String {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(config.cloud.bucket)
            .key(key)
            .build()

        val presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.of(4, ChronoUnit.HOURS))
            .getObjectRequest(getObjectRequest)
            .build()

        val presignedUrl = presigner.presignGetObject(presignRequest)
        return presignedUrl.url().toString()
    }
}