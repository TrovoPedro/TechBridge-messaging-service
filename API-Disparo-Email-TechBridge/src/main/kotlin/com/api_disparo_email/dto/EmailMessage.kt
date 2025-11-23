package com.api_disparo_email.dto

import com.api_disparo_email.enums.EmailTipo

data class EmailMessage(
    var to: String,
    val subject: String,
    val body: String,
    val tipo: EmailTipo,
    val isHtml: Boolean
)
