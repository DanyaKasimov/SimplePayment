//package app.security.filter
//
//import app.security.authentication.TokenAuthentication
//import app.service.JwtService
//import com.auth0.jwt.interfaces.DecodedJWT
//import io.jsonwebtoken.ExpiredJwtException
//import jakarta.servlet.FilterChain
//import jakarta.servlet.ServletException
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.OncePerRequestFilter
//import java.io.IOException
//
//@Component
//class TokenFilter(
//    private val jwtService: JwtService,
//    private val userDetailsService: UserDetailsService
//) : OncePerRequestFilter() {
//
//    @Throws(ServletException::class, IOException::class)
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        try {
//            val jwt = getTokenFromRequest(request)
//
//            if (jwt != null && SecurityContextHolder.getContext().authentication == null) {
//                val decodedJWT = jwtService.validate(jwt)
//                val username = decodedJWT.getClaim("username").asString()
//
//                username?.let {
//                    val userDetails = userDetailsService.loadUserByUsername(it)
//                    setAuthentication(jwt, userDetails)
//                }
//            }
//        } catch (e: ExpiredJwtException) {
//            throw RuntimeException("Токен истек: ${e.message}")
//        } catch (e: Exception) {
//            throw RuntimeException("Недействительный токен: ${e.message}")
//        }
//
//        filterChain.doFilter(request, response)
//    }
//
//    private fun getTokenFromRequest(request: HttpServletRequest): String? {
//        val headerAuth = request.getHeader("Authorization")
//        return if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
//            headerAuth.substring(7)
//        } else {
//            null
//        }
//    }
//
//    private fun setAuthentication(jwt: String, userDetails: UserDetails) {
//        val authentication = TokenAuthentication(jwt).apply {
//            isAuthenticated = true
//            this.setDetails(userDetails)
//        }
//        SecurityContextHolder.getContext().authentication = authentication
//    }
//}