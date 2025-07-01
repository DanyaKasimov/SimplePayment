package app.security.filter

import app.exception.AuthenticationException
import app.security.authentication.TokenAuthentication
import app.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class TokenFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {
        try {
            val jwt = getTokenFromRequest(request)

            if (jwt != null && SecurityContextHolder.getContext().authentication == null) {
                val decodedJWT = jwtService.validate(jwt)

                val email = decodedJWT.getClaim("email").asString()
                val role = decodedJWT.getClaim("role").asString()

                val authentication = TokenAuthentication(
                    token = jwt,
                    principal = email,
                    authorities = listOf(SimpleGrantedAuthority(role))
                )

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: ExpiredJwtException) {
            throw AuthenticationException("Токен истек: ${e.message}")
        } catch (e: Exception) {
            throw AuthenticationException("Недействительный токен: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7)
        } else {
            null
        }
    }
}