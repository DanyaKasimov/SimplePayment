package app.logger

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.IndexRequest
import org.springframework.stereotype.Service

@Service
class ElasticLogService(private val elasticsearchClient: ElasticsearchClient) {

    fun logToElastic(entry: LogEntry) {
        try {
            val request = IndexRequest.Builder<LogEntry>()
                .index("application-logs")
                .document(entry)
                .build()

            elasticsearchClient.index(request)
        } catch (ex: Exception) {
            println("Ошибка при отправке лога в Elasticsearch: ${ex.message}")
        }
    }
}