package app.logger

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant

@Aspect
@Component
class RequestLoggingAspect(
    private val elasticLogService: ElasticLogService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    fun restController() {}

    @Around("restController()")
    fun logRequest(joinPoint: ProceedingJoinPoint): Any? {
        val start = Instant.now()
        val methodName = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        val args = joinPoint.args.map { it?.toString() ?: "null" }

        val logEntry = LogEntry(
            serviceName = "payment-service",
            methodName = methodName,
            className = className,
            arguments = args
        )

        return try {
            val result = joinPoint.proceed()
            val duration = Duration.between(start, Instant.now()).toMillis()
            
            val successEntry = logEntry.copy(
                executionTimeMs = duration,
                result = result?.toString()?.take(200)
            )
            
            elasticLogService.logToElastic(successEntry)
            logger.info("Успешное выполнение: $className.$methodName за $duration мс")
            result
        } catch (ex: Exception) {
            val errorEntry = logEntry.copy(
                error = ex.message,
                stackTrace = ex.stackTraceToString()
            )
            elasticLogService.logToElastic(errorEntry)
            logger.error("Ошибка в $className.$methodName: ${ex.message}", ex)
            throw ex
        }
    }
}