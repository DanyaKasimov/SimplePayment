package app.dto

import java.util.UUID

data class PaymentDTO(val senderWallet: UUID, val recipientWallet: UUID, val amount: Long) {
}