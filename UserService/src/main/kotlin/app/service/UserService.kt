package app.service

import app.dto.SignUpDTO
import app.entity.User

interface UserService {

    fun findByEmail(email: String): User

    fun create(signUpDTO: SignUpDTO): User
}