package app.service

import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.Authentication


interface JwtService {

    fun validate(token: String): DecodedJWT
}
