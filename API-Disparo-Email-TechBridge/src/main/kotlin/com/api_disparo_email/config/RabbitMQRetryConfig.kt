package com.api_disparo_email.config

import org.springframework.amqp.core.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQRetryConfig {

    @Value("\${rabbitmq.queue.email:ea.email.queue}")
    private lateinit var emailQueueName: String

    private val dlqName get() = "${emailQueueName}.dlq"
    private val retryQueueName get() = "${emailQueueName}.retry"
    private val retryTtlMs = 60000 // Aqui eu estou configurando quanto tempo até ele dar o retry no email (1 minuto)

    @Value("\${rabbitmq.exchange:ea.exchange}")
    private lateinit var emailExchangeName: String

    private val dlxName get() = "${emailExchangeName}.dlx"

    @Value("\${rabbitmq.routing.email:ea.email.routing}")
    private lateinit var routingKey: String

    private val dlqRouting get() = "${routingKey}.dlq"
    private val retryRouting get() = "${routingKey}.retry"

    // -----------------------------
    //  EXCHANGES
    // -----------------------------

    @Bean
    fun emailDlx(): TopicExchange =
        TopicExchange(dlxName)

    // Retry não precisa de uma exchange própria
    // porque vai usar a DLX automaticamente


    // -----------------------------
    //  DEAD LETTER QUEUE (DLQ)
    // -----------------------------

    @Bean
    fun emailDlq(): Queue =
        QueueBuilder.durable(dlqName)
            .withArgument("x-dead-letter-exchange", emailExchangeName)
            .withArgument("x-dead-letter-routing-key", routingKey)
            .build()

    @Bean
    fun emailDlqBinding(): Binding =
        BindingBuilder.bind(emailDlq())
            .to(emailDlx())
            .with(dlqRouting)


    // -----------------------------
    //  RETRY QUEUE (1 minuto)
    // -----------------------------

    @Bean
    fun emailRetryQueue(): Queue =
        QueueBuilder.durable(retryQueueName)
            .withArgument("x-message-ttl", retryTtlMs)
            .withArgument("x-dead-letter-exchange", emailExchangeName)
            .withArgument("x-dead-letter-routing-key", routingKey)
            .build()

    @Bean
    fun retryBinding(): Binding =
        BindingBuilder.bind(emailRetryQueue())
            .to(emailDlx())
            .with(retryRouting)
}