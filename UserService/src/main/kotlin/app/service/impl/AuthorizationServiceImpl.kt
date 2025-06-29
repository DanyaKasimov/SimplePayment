package app.service.impl

import app.constants.Message
import app.dto.*
import app.entity.User
import app.service.*

import org.springframework.stereotype.Service

@Service
class AuthorizationServiceImpl(private val verificationService: VerificationService,
                               private val authenticationService: AuthenticationService,
                               private val userService: UserService,
                               private val notificationService: NotificationService) : AuthorizationService {

    override fun requestVerification(requestVerificationDTO: RequestVerificationDTO): MessageDTO {
        val code: String = verificationService.generateVerificationCode()
        val email: String = requestVerificationDTO.email

        verificationService.storeVerificationCode(email, code)
        notificationService.sendEmail(email, Message.code(code))

        return MessageDTO("Код отправлен на почту: $email")
    }

    override fun verifyCode(verificationCodeDTO: VerifyCodeDTO): MessageDTO {
        val isMatches: Boolean = verificationService.verifyCode(verificationCodeDTO.email,
                                                                verificationCodeDTO.code)
        return if (isMatches) MessageDTO("Почта подтверждена") else throw RuntimeException("Неверный код")

    }

    override fun signUp(signUpDTO: SignUpDTO): MessageDTO {
        if (!verificationService.emailIsVerify(signUpDTO.email)) {
            throw RuntimeException("Почта [${signUpDTO.email}] не подтверждена")
        }

        val user: User = userService.create(signUpDTO)

        verificationService.removeVerificationCode(user.email)
        notificationService.sendEmail(signUpDTO.email, Message.signupUser(user.name, user.surname))

        val jwt: String = authenticationService.authenticate(signUpDTO.email, signUpDTO.password);
        return MessageDTO(jwt)
    }

    override fun signIn(signInDTO: SignInDTO): MessageDTO {
        userService.findByEmail(signInDTO.email)
        val jwt: String = authenticationService.authenticate(signInDTO.email, signInDTO.password);
        return MessageDTO(jwt)
    }
}