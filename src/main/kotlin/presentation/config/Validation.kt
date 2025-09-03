package com.simbiri.presentation.config

import com.simbiri.presentation.validator.validateQuizQuestion
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation(){
    install(RequestValidation) {
        validateQuizQuestion()
    }
}