package app.config.kafka

import app.config.AppConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*


@Configuration
@EnableKafka
open class KafkaConfig(
        private val config: AppConfig
) {

    @Bean
    open fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    open fun consumerFactory(): DefaultKafkaConsumerFactory<String, String> {
        val configProps = mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to config.kafka.bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to config.kafka.group,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
        return DefaultKafkaConsumerFactory(configProps)
    }

}