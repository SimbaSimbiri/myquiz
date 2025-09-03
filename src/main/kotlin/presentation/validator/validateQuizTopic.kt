package com.simbiri.presentation.validator

import com.simbiri.domain.model.QuizTopic
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateQuizTopic() {
    validate<QuizTopic> { quizTopic ->
        when {
            quizTopic.name.isBlank() || quizTopic.name.length < 3 -> {
                ValidationResult.Invalid(reason = "Minimum logical topic length wasn't met")
            }

            quizTopic.imageUrl.isBlank() -> {
                ValidationResult.Invalid(reason = "Image url field is required")
            }

            quizTopic.code < 0 -> {
                ValidationResult.Invalid(reason = "Topic code must be a real int")
            }

            else -> {
                ValidationResult.Valid
            }
        }
    }
}