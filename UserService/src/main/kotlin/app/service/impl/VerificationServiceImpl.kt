package app.service.impl

import app.config.AppConfig
import app.service.VerificationService
import org.springframework.stereotype.Service
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Duration
import java.util.*

@Service
class VerificationServiceImpl(private val redisTemplate: StringRedisTemplate,
                              private val config: AppConfig)  : VerificationService {


    override fun generateVerificationCode(): String {
        val random = Random()
        val code = 100000 + random.nextInt(900000)
        return code.toString()
    }

    override fun storeVerificationCode(email: String, verificationCode: String) {
        val key = config.redis.prefix + email
        redisTemplate.opsForValue().set(key, verificationCode, Duration.ofMinutes(10))
    }

    override fun verifyCode(email: String, code: String): Boolean {
        val key = config.redis.prefix + email
        val storedCode = redisTemplate.opsForValue().get(key)
        val matched = code == storedCode
        if (matched) {
            redisTemplate.opsForValue().set(email, "", Duration.ofMinutes(30))
        }
        return matched
    }

    override fun removeVerificationCode(email: String) {
        val key = config.redis.prefix + email
        redisTemplate.delete(key)
    }

    override fun emailIsVerify(email: String): Boolean {
        return redisTemplate.hasKey(email)
    }
}