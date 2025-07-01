package app.controller

import app.api.PaymentApi
import app.dto.MessageDTO
import app.dto.PaymentDTO
import app.service.PaymentService
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
open class PaymentController(private val paymentService: PaymentService) : PaymentApi {

    override fun payment(paymentDTO: PaymentDTO,
                         @RequestHeader("Authorization") authorization: String): MessageDTO =
        paymentService.createPayment(paymentDTO, authorization)
}