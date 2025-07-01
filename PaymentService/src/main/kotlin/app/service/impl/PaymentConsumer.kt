package app.service.impl

import app.clients.NotificationClient
import app.clients.UserClient
import app.constants.Message
import app.dto.EmailDTO
import app.dto.PaymentDTO
import app.dto.PaymentKafkaDTO
import app.entity.Payment
import app.entity.Wallet
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
                           private val notificationClient: NotificationClient,
                           private val userClient: UserClient,
                           private val objectMapper: ObjectMapper) {

    @KafkaListener(
        topics = ["\${app.kafka.topics.payment}"],
        groupId = "\${app.kafka.group}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    open fun handlePaymentEvent(message: String) {
        val paymentKafkaDTO: PaymentKafkaDTO = objectMapper.readValue(message, PaymentKafkaDTO::class.java)

        val sender: Wallet = walletService.getWalletById(paymentKafkaDTO.senderWallet)
        val recipient: Wallet = walletService.getWalletById(paymentKafkaDTO.recipientWallet)

        changeBalance(sender, recipient, paymentKafkaDTO)

        val checkKey: String =
            s3Service.upload(generateCheck(paymentKafkaDTO), "check-${LocalDateTime.now()}.txt")

        savePayment(paymentKafkaDTO, checkKey)
        sendEmails(sender, recipient, paymentKafkaDTO, checkKey)
    }

    private fun generateCheck(paymentDTO: PaymentKafkaDTO): File {
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

    private fun changeBalance(sender: Wallet, recipient: Wallet, paymentKafkaDTO: PaymentKafkaDTO) {
        sender.changeBalance(-paymentKafkaDTO.amount)
        recipient.changeBalance(paymentKafkaDTO.amount)

        walletService.saveAll(listOf(sender, recipient))
    }

    private fun savePayment(paymentKafkaDTO: PaymentKafkaDTO, checkKey: String) {
        val paymentDTO = PaymentDTO(paymentKafkaDTO.senderWallet, paymentKafkaDTO.recipientWallet, paymentKafkaDTO.amount)
        val payment: Payment = paymentDTO.toEntity(walletService)
        payment.checkKey = checkKey
        paymentRepository.save(payment)
    }

    private fun sendEmails(sender: Wallet, recipient: Wallet, paymentKafkaDTO: PaymentKafkaDTO, checkKey: String) {
        val token = paymentKafkaDTO.token;
        val senderUser = userClient.getUser(sender.userId, token);
        val recipientUser = userClient.getUser(recipient.userId, token);

        val checkUrl = s3Service.generateUrl(checkKey)

        val emailSenderDTO = EmailDTO(senderUser.email,
            Message.paymentSender(senderUser.name, senderUser.surname, paymentKafkaDTO.amount, checkUrl))
        val emailRecipientDTO = EmailDTO(recipientUser.email,
            Message.paymentRecipient(senderUser.name, senderUser.surname, paymentKafkaDTO.amount, checkUrl))

        notificationClient.sendEmail(emailSenderDTO, token)
        notificationClient.sendEmail(emailRecipientDTO, token)
    }
}