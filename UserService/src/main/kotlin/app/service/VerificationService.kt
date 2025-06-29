package app.service

interface VerificationService {

    fun generateVerificationCode() : String

    fun storeVerificationCode(email: String, verificationCode: String)

    fun verifyCode(email: String, code: String): Boolean

    fun removeVerificationCode(email: String)

    fun emailIsVerify(email: String): Boolean
}