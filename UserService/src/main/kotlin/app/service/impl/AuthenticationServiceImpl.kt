package app.service.impl

import app.service.AuthenticationService
import app.service.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticateServiceImpl(private val authenticationManager: AuthenticationManager,
                              private val jwtService: JwtService) : AuthenticationService {

    override fun authenticate(email: String, password: String): String {
        return try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            jwtService.generateToken(authentication)
        } catch (e: BadCredentialsException) {
            throw RuntimeException("Ошибка входа: Неверные учетные данные.")
        }
    }
}