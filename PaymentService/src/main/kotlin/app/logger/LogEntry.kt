package app.logger

import java.time.Instant

data class LogEntry(
    val timestamp: Instant = Instant.now(),
    val serviceName: String,
    val methodName: String,
    val className: String,
    val arguments: List<String>,
    val executionTimeMs: Long? = null,
    val result: String? = null,
    val error: String? = null,
    val stackTrace: String? = null
)