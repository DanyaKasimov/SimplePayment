package app.service

import app.dto.MessageDTO
import app.dto.PaymentDTO

interface PaymentService {

    fun createPayment(paymentDTO: PaymentDTO, token: String): MessageDTO
}