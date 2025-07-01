package app.service

import app.config.AppConfig
import app.service.JwtService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Service

@Service
class JwtServiceImpl(private val config: AppConfig) : JwtService {

    override fun validate(token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC256(config.jwt.secret))
            .build()
            .verify(token)
    }
}
