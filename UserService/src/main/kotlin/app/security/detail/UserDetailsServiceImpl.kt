package app.security.detail

import app.entity.User
import app.exceptions.NotFoundDataException
import app.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
            ?: throw NotFoundDataException("Пользователь с $email не найден")

        return UserDetailsImpl(user)
    }
}