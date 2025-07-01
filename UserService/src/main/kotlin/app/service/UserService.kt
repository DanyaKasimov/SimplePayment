package app.service

import app.dto.SignUpDTO
import app.dto.UserDTO
import app.entity.User
import java.util.UUID

interface UserService {

    fun findByEmail(email: String): User

    fun create(signUpDTO: SignUpDTO): User

    fun existsById(id: UUID): Boolean

    fun findById(id: UUID): UserDTO
}