package app.clients

import feign.RequestInterceptor
import feign.RequestTemplate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@Component
class FeignClientInterceptor : RequestInterceptor {

    private val logger = LoggerFactory.getLogger(FeignClientInterceptor::class.java)
    
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    override fun apply(template: RequestTemplate) {
        val jwtToken = getJwtFromCurrentRequest()

        if (!jwtToken.isNullOrBlank()) {
            logger.debug("Добавляем JWT токен в заголовок.")
            template.header(AUTHORIZATION_HEADER, jwtToken)
        } else {
            logger.warn("JWT токен отсутствует в заголовке Authorization.")
        }
    }

    private fun getJwtFromCurrentRequest(): String? {
        val attributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request = attributes?.request ?: return null

        val authHeader = request.getHeader(AUTHORIZATION_HEADER)
        return if (!authHeader.isNullOrBlank() && authHeader.startsWith(BEARER_PREFIX)) {
            authHeader
        } else {
            null
        }
    }
}