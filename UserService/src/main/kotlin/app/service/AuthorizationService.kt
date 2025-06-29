package app.service

import app.dto.*

interface AuthorizationService {

    fun requestVerification(requestVerificationDTO: RequestVerificationDTO) : MessageDTO

    fun verifyCode(verificationCodeDTO: VerifyCodeDTO) : MessageDTO

    fun signUp(signUpDTO: SignUpDTO) : MessageDTO

    fun signIn(signInDTO: SignInDTO) : MessageDTO
}