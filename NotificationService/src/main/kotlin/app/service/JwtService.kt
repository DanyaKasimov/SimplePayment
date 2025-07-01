package app.service

import com.auth0.jwt.interfaces.DecodedJWT


interface JwtService {

    fun validate(token: String): DecodedJWT
}
