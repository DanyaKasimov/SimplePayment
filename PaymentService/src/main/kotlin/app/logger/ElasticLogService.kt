package app.logger

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.IndexRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ElasticLogService(private val elasticsearchClient: ElasticsearchClient) {

    fun logToElastic(entry: LogEntry) {
        try {
            val indexName = "application-logs-" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

            val request = IndexRequest.Builder<LogEntry>()
                .index(indexName)
                .document(entry)
                .build()


            elasticsearchClient.index(request)
        } catch (ex: Exception) {
            throw RuntimeException("Ошибка при отправке лога в Elasticsearch: ${ex.message}")
        }
    }
}