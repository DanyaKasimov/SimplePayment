package app.service.impl

import app.config.AppConfig
import app.dto.MessageDTO
import app.dto.PaymentDTO
import app.dto.PaymentKafkaDTO
import app.exceptions.InvalidDataException
import app.service.PaymentService
import app.service.WalletService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
open class PaymentServiceImpl(private val kafkaTemplate: KafkaTemplate<String, String>,
                              private val config: AppConfig,
                              private val walletService: WalletService,
                              private val objectMapper: ObjectMapper) : PaymentService {


    override fun createPayment(paymentDTO: PaymentDTO, token: String): MessageDTO {
        val sender = walletService.getWalletById(paymentDTO.senderWallet)
        if (sender.balance < paymentDTO.amount) {
            throw InvalidDataException("Недостаточно средств для перевода. Проверьте баланс")
        }

        val paymentKafkaDTO: PaymentKafkaDTO = PaymentKafkaDTO.mapFromPaymentDTO(paymentDTO, token)
        kafkaTemplate.send(config.kafka.topics.payment, objectMapper.writeValueAsString(paymentKafkaDTO))
        return MessageDTO("Платеж инициирован")
    }

}