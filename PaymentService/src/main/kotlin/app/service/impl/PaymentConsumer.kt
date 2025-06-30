package app.service.impl

import app.dto.PaymentDTO
import app.mapper.toEntity
import app.repository.PaymentRepository
import app.service.WalletService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class PaymentConsumer(private val walletService: WalletService,
                           private val paymentRepository: PaymentRepository,
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

        paymentRepository.save(paymentDTO.toEntity(walletService))
    }
}