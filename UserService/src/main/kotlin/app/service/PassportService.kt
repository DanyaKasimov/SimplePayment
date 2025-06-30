package app.service

import app.dto.MessageDTO
import app.dto.PassportDTO
import org.springframework.web.multipart.MultipartFile

interface PassportService {

    fun uploadPassport(series: String, number: String, file: MultipartFile) : MessageDTO

    fun getPassport(series: String, number: String) : PassportDTO

}