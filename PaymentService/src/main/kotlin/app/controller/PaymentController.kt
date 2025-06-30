package app.controller

import app.api.PaymentApi
import app.dto.MessageDTO
import app.dto.PaymentDTO
import app.service.PaymentService
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(private val paymentService: PaymentService) : PaymentApi {

    override fun payment(paymentDTO: PaymentDTO): MessageDTO = paymentService.createPayment(paymentDTO)
}