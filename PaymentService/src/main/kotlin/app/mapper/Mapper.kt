package app.mapper

import app.dto.PaymentDTO
import app.entity.Payment
import app.service.WalletService


fun PaymentDTO.toEntity(walletService: WalletService): Payment {
    val payment = Payment()
    payment.amount = this.amount
    payment.senderWallet = walletService.getWalletById(this.senderWallet)
    payment.recipientWallet = walletService.getWalletById(this.recipientWallet)
    return payment
}



