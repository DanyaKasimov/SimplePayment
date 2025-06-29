package app.service

import app.dto.SignInDTO

interface AuthenticationService {

    fun authenticate(email: String, password: String): String
}