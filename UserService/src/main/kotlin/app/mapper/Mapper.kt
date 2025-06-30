package app.mapper

import app.dto.PassportDTO
import app.dto.SignUpDTO
import app.entity.Passport
import app.entity.User
import app.objectStorage.S3Service
import org.springframework.security.crypto.password.PasswordEncoder

fun SignUpDTO.toEntity(passwordEncoder: PasswordEncoder): User {
    val user = User()
    user.name = this.name
    user.surname = this.surname
    user.middle = this.middle
    user.birthday = this.birthday
    user.gender = this.gender
    user.inn = this.inn
    user.address = this.address
    user.email = this.email
    user.password = passwordEncoder.encode(this.password)
    user.role = app.constants.Role.USER
    user.passport = toPassportEntity(this)
    return user
}

fun toPassportEntity(signUpDTO: SignUpDTO): Passport {
    val passport = Passport()
    passport.series = signUpDTO.passportSeria
    passport.number = signUpDTO.passportNumber
    return passport
}

fun Passport.toDto(s3Service: S3Service): PassportDTO {
    return PassportDTO(
        id = this.id,
        series = this.series,
        number = this.number,
        s3Url = s3Service.generateUrl(this.s3Key)
    )
}