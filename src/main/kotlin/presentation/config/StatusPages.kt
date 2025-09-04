package com.simbiri.presentation.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    // this configuration works hand in hand with the validationConfig to display
    // 400 status errors when the received json fields is of the wrong format
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(message = cause.reasons.joinToString(), status = HttpStatusCode.BadRequest)
        }
    }
}