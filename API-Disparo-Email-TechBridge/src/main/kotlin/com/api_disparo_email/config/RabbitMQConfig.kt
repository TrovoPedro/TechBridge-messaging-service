package com.api_disparo_email.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Value("\${rabbitmq.queue.email:ea.email.queue}")
    private lateinit var queueName: String

    @Value("\${rabbitmq.exchange:ea.exchange}")
    private lateinit var exchangeName: String

    @Value("\${rabbitmq.routing.email:ea.email.routing}")
    private lateinit var routingKey: String

    @Bean
    fun filaEmail(): Queue {
        return Queue("fila.email", true) // durable
    }

    @Bean
    fun queue(): Queue = Queue(queueName, true)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(exchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding =
        BindingBuilder.bind(queue).to(exchange).with(routingKey)
}
