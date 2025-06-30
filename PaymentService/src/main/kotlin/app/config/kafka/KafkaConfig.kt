package app.config.kafka

import app.config.AppConfig
import jakarta.persistence.EntityManagerFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.transaction.ChainedTransactionManager
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import kotlin.collections.HashMap

@Configuration
@EnableKafka
open class KafkaConfig(private val config: AppConfig) {

    @Bean
    open fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        factory.setConcurrency(1)
        factory.isBatchListener = false
        factory.containerProperties.transactionManager = kafkaTransactionManager()

        return factory
    }

    @Bean
    open fun consumerFactory(): DefaultKafkaConsumerFactory<String, String> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = config.kafka.bootstrapServers
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = config.kafka.group
        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configProps[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        configProps[ConsumerConfig.ISOLATION_LEVEL_CONFIG] = "read_committed"
        return DefaultKafkaConsumerFactory(configProps)
    }

    @Bean
    open fun kafkaTemplate(): KafkaTemplate<String, String> {
        val template = KafkaTemplate(producerFactory())
        template.transactionIdPrefix = "txn-prefix-"
        return template
    }

    @Bean
    open fun producerFactory(): ProducerFactory<String, String> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = config.kafka.bootstrapServers
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true
        configProps[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = "txn-id-${UUID.randomUUID()}"
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    open fun kafkaTransactionManager(): KafkaTransactionManager<String, String> {
        return KafkaTransactionManager(producerFactory())
    }

    @Bean
    open fun jpaTransactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    @Bean(name = ["transactionManager"])
    open fun chainedTransactionManager(
        jpaTransactionManager: PlatformTransactionManager,
        kafkaTransactionManager: KafkaTransactionManager<String, String>
    ): ChainedTransactionManager {
        return ChainedTransactionManager(jpaTransactionManager, kafkaTransactionManager)
    }
}