package com.api_disparo_email.consumer

import com.api_disparo_email.dto.EmailMessage
import com.api_disparo_email.enums.EmailTipo
import com.api_disparo_email.service.EmailService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class EmailConsumer(
    private val emailService: EmailService
) {

    @RabbitListener(queues = ["ea.email.queue"])
    fun receive(emailMessage: EmailMessage) {
        println("Mensagem recebida: $emailMessage")

        val body = montarMensagem(emailMessage)

        emailService.enviarEmail(
            to = emailMessage.to,
            subject = emailMessage.subject,
            body = body
        )
    }

    private fun montarMensagem(msg: EmailMessage): String {
        return when (msg.tipo) {
            EmailTipo.EVENTO_CANCELADO ->
                "Seu evento foi cancelado. Motivo: ${msg.body}"

            EmailTipo.USUARIO_REMOVIDO ->
                "Você foi removido do evento. Motivo: ${msg.body}"

            else -> msg.body
        }
    }
}

