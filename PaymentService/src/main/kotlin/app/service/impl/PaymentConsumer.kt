package app.service.impl

import app.dto.PaymentDTO
import app.entity.Payment
import app.mapper.toEntity
import app.objectStorage.S3Service
import app.repository.PaymentRepository
import app.service.WalletService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
open class PaymentConsumer(private val walletService: WalletService,
                           private val paymentRepository: PaymentRepository,
                           private val s3Service: S3Service,
                           private val objectMapper: ObjectMapper) {

    @KafkaListener(
        topics = ["\${app.kafka.topics.payment}"],
        groupId = "\${app.kafka.group}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    open fun handlePaymentEvent(message: String) {
        val paymentDTO = objectMapper.readValue(message, PaymentDTO::class.java)

        val sender = walletService.getWalletById(paymentDTO.senderWallet)
        val recipient = walletService.getWalletById(paymentDTO.recipientWallet)

        sender.changeBalance(-paymentDTO.amount)
        recipient.changeBalance(paymentDTO.amount)

        walletService.saveAll(listOf(sender, recipient))

        val checkKey: String = s3Service.upload(generateCheck(paymentDTO), "check-${LocalDateTime.now()}.txt")

        val payment: Payment = paymentDTO.toEntity(walletService)
        payment.checkKey = checkKey
        paymentRepository.save(payment)
    }

    private fun generateCheck(paymentDTO: PaymentDTO): File {
        val content = """
        Чек №${(1000..9999).random()}
        Дата: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}
        Сумма: ${paymentDTO.amount} руб.
        Отправитель: ${paymentDTO.senderWallet}
        Получатель: ${paymentDTO.recipientWallet}
    """.trimIndent()

        val tempFile = File.createTempFile("check_", ".txt")
        tempFile.writeText(content, StandardCharsets.UTF_8)
        return tempFile
    }
}