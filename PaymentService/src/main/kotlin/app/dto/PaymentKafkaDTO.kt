package app.dto

import java.util.*

data class PaymentKafkaDTO(
    val senderWallet: UUID,
    val recipientWallet: UUID,
    val amount: Long,
    val token: String
) {
    companion object {
        fun mapFromPaymentDTO(paymentDTO: PaymentDTO, token: String): PaymentKafkaDTO {
            return PaymentKafkaDTO(
                senderWallet = paymentDTO.senderWallet,
                recipientWallet = paymentDTO.recipientWallet,
                amount = paymentDTO.amount,
                token = token
            )
        }
    }
}