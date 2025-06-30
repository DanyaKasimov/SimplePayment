package app.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ElasticsearchConfig(private val config: AppConfig) {
    @Bean
    open fun elasticsearchClient(): ElasticsearchClient {
        val restClient = RestClient.builder(HttpHost(config.elastic.host, config.elastic.port)).build()
        val transport: ElasticsearchTransport = RestClientTransport(
            restClient,
            JacksonJsonpMapper()
        )
        return ElasticsearchClient(transport)
    }
}