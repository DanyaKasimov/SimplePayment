package app.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ElasticsearchConfig(private val config: AppConfig) {
    @Bean
    open fun elasticsearchClient(): ElasticsearchClient {
        val restClient = RestClient.builder(HttpHost(config.elastic.host, config.elastic.port)).build()

        val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        val mapper = JacksonJsonpMapper(objectMapper)

        val transport: ElasticsearchTransport = RestClientTransport(restClient, mapper)
        return ElasticsearchClient(transport)
    }
}