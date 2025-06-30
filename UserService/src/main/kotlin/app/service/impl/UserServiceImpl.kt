package app.service.impl

import app.dto.SignUpDTO
import app.entity.User
import app.exceptions.InvalidDataException
import app.exceptions.NotFoundDataException
import app.mapper.toEntity
import app.repository.UserRepository
import app.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val passwordEncoder: PasswordEncoder) : UserService {

    override fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw NotFoundDataException("Пользователь не найден.")
    }

    override fun create(signUpDTO: SignUpDTO): User {
        if (userRepository.existsByEmail(signUpDTO.email)) {
            throw InvalidDataException("Почта [${signUpDTO.email}] уже зарегистрирована")
        }
        val user: User = signUpDTO.toEntity(passwordEncoder)
        return userRepository.save(user)
    }
}