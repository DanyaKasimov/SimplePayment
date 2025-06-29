package app.controller

import app.api.AuthApi
import app.dto.*
import app.service.AuthorizationService
import org.springframework.web.bind.annotation.RestController

@RestController
open class AuthController(private val authorizationService: AuthorizationService) : AuthApi {

    override fun requestVerification(verificationDTO: RequestVerificationDTO): MessageDTO =
        authorizationService.requestVerification(verificationDTO)

    override fun verifyCode(verifyCodeDTO: VerifyCodeDTO): MessageDTO = authorizationService.verifyCode(verifyCodeDTO)

    override fun signup(signUpDTO: SignUpDTO): MessageDTO = authorizationService.signUp(signUpDTO)

    override fun signin(signInDTO: SignInDTO): MessageDTO = authorizationService.signIn(signInDTO)
}