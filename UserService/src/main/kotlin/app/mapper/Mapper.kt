package app.mapper

import app.dto.SignUpDTO
import app.entity.Passport
import app.entity.User
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