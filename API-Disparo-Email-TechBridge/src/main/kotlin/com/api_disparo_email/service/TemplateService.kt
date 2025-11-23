package com.api_disparo_email.service

import com.api_disparo_email.dto.EmailMessage
import com.api_disparo_email.enums.EmailTipo
import org.springframework.stereotype.Service

@Service
class TemplateService {

    fun gerarTemplate(
        tipo: EmailTipo,
        nomeUsuario: String,
        nomeTrilha: String,
        dataEvento: String,
    ): EmailMessage {

        return when (tipo) {

            EmailTipo.USUARIO_REMOVIDO -> EmailMessage(
                to = "",
                subject = "Você foi removido da trilha $nomeTrilha",
                body = """
                    <html>
                        <body>
                            <p>Olá, $nomeUsuario!</p>
                            <p>Informamos que você foi removido da trilha <b>$nomeTrilha</b>, que estava prevista para o dia <b>$dataEvento</b>.</p>
                            <p>Caso tenha dúvidas ou acredite que isso foi um engano, entre em contato com o guia responsável ou com o suporte da Equilibrium Adventure.</p>
                            <p>Atenciosamente,<br/>Equipe Equilibrium Adventure</p>
                        </body>
                    </html>
                """.trimIndent(),
                tipo = tipo,
                isHtml = true // necessário para envio HTML
            )

            EmailTipo.EVENTO_CANCELADO -> EmailMessage(
                to = "",
                subject = "Evento cancelado: $nomeTrilha",
                body = """
                    <html>
                        <body>
                            <p>Olá, $nomeUsuario!</p>
                            <p>O evento <b>$nomeTrilha</b>, previsto para o dia <b>$dataEvento</b>, foi cancelado.</p>
                            <p>Pedimos desculpas pelo transtorno. Caso seja necessário, nossa equipe está disponível para auxiliar na inscrição em outras trilhas.</p>
                            <p>Atenciosamente,<br/>Equipe Equilibrium Adventure</p>
                        </body>
                    </html>
                """.trimIndent(),
                tipo = tipo,
                isHtml = true
            )
        }
    }
}
