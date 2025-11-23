package com.api_disparo_email.controller

import com.api_disparo_email.dto.EmailMessage
import com.api_disparo_email.enums.EmailTipo
import com.api_disparo_email.service.TemplateService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class EmailRequest(
    val to: String,
    val tipo: String,
    val nomeUsuario: String,
    val nomeTrilha: String,
    val dataEvento: String,
    val motivo: String
)

@RestController
@RequestMapping("/emails")
class EmailController(
    private val rabbitTemplate: RabbitTemplate,
    private val templateService: TemplateService,
    @Value("\${rabbitmq.exchange}") private val exchange: String,
    @Value("\${rabbitmq.routing.email}") private val routingKey: String
) {

    init {
        println("EmailController iniciado!")
    }

    @PostMapping("/send")
    fun send(@RequestBody request: EmailRequest): ResponseEntity<String> {
        // Gera o template de e-mail
        val emailMessage: EmailMessage = templateService.gerarTemplate(
            tipo = EmailTipo.valueOf(request.tipo),
            nomeUsuario = request.nomeUsuario,
            nomeTrilha = request.nomeTrilha,
            dataEvento = request.dataEvento,
        )

        // Define o destinatário
        emailMessage.to = request.to

        // Envia para a fila
        rabbitTemplate.convertAndSend(exchange, routingKey, emailMessage)
        return ResponseEntity.ok("Email enviado para a fila")
    }
}
