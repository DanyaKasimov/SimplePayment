package app.controller

import app.api.PassportApi
import app.dto.MessageDTO
import app.dto.PassportDTO
import app.service.PassportService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
open class PassportController(private val passportService: PassportService) : PassportApi {

    override fun addPassportScan(series: String, number: String, scan: MultipartFile): MessageDTO =
        passportService.uploadPassport(series, number, scan)

    override fun getPassport(series: String, number: String): PassportDTO = passportService.getPassport(series, number)

}