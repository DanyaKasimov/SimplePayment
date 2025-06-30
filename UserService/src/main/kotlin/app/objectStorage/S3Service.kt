package app.objectStorage

import app.config.AppConfig
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class S3Service(private val s3: S3Client,
                private val presigner: S3Presigner,
                private val config: AppConfig) {

    fun upload(file: MultipartFile): String {
        val key = file.originalFilename ?: "upload-${LocalDateTime.now()}"

        val temp = Files.createTempFile("upload", key)
        file.inputStream.use { inp ->
            Files.copy(inp, temp, StandardCopyOption.REPLACE_EXISTING)
        }

        val putRequest = PutObjectRequest.builder()
            .bucket(config.cloud.bucket)
            .key(key)
            .contentType(file.contentType)
            .build()

        s3.putObject(putRequest, temp)
        Files.deleteIfExists(temp)

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