package app.service.impl

import app.dto.MessageDTO
import app.dto.PassportDTO
import app.entity.Passport
import app.exceptions.NotFoundDataException
import app.mapper.toDto
import app.objectStorage.S3Service
import app.repository.PassportRepository
import app.service.PassportService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PassportServiceImpl(private val passportRepository: PassportRepository,
                          private val s3Service: S3Service) : PassportService {

    override fun uploadPassport(series: String, number: String, file: MultipartFile) : MessageDTO {
        val passport: Passport = passportRepository.findBySeriesAndNumber(series, number) ?:
                throw NotFoundDataException("Паспорт с серией=$series и номером=$number не найден")

        val key: String = s3Service.upload(file)
        passport.s3Key = key
        passportRepository.save(passport)

        return MessageDTO(s3Service.generateUrl(key))
    }

    override fun getPassport(series: String, number: String) : PassportDTO {
        val passport: Passport = passportRepository.findBySeriesAndNumber(series, number) ?:
                throw NotFoundDataException("Паспорт с серией=$series и номером=$number не найден")

        return passport.toDto(s3Service)
    }
}