//package app.service.impl
//
//import app.config.AppConfig
//import app.security.detail.UserDetailsImpl
//import app.service.JwtService
//import com.auth0.jwt.JWT
//import com.auth0.jwt.algorithms.Algorithm
//import com.auth0.jwt.interfaces.DecodedJWT
//import org.springframework.security.core.Authentication
//import org.springframework.stereotype.Service
//import java.util.*
//
//@Service
//class JwtServiceImpl(private val config: AppConfig) : JwtService {
//
//    override fun generateToken(authentication: Authentication): String {
//        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
//        return JWT.create()
//            .withClaim("username", userDetails.getUsername())
//            .withClaim("name", userDetails.getName())
//            .withClaim("surname", userDetails.getSurname())
//            .withClaim("email", userDetails.getEmail())
//            .withClaim("id", java.lang.String.valueOf(userDetails.getId()))
//            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//            .sign(Algorithm.HMAC256(config.jwt.secret))
//    }
//
//    override fun validate(token: String): DecodedJWT {
//        return JWT.require(Algorithm.HMAC256(config.jwt.secret))
//            .build()
//            .verify(token)
//    }
//}
