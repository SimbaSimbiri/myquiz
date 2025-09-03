package com.simbiri.presentation.validator

import com.simbiri.domain.model.QuizQuestion
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateQuizQuestion() {
    validate<QuizQuestion> { quizQuestion ->
        when {
            quizQuestion.question.isBlank() || quizQuestion.question.length < 10-> {
                ValidationResult.Invalid(reason = "Minimum logical question length wasn't met")
            }

            quizQuestion.correctAnswer.isBlank() -> {
                ValidationResult.Invalid(reason = "Correct Answer field is required")
            }

            quizQuestion.incorrectAnswers.isEmpty() -> {
                ValidationResult.Invalid(reason = "Incorrect Answers field must not be empty")
            }

            quizQuestion.incorrectAnswers.any { it.isBlank() } || quizQuestion.incorrectAnswers.size < 3  -> {
                ValidationResult.Invalid(reason = "All answer choices in IncorrectAnswers field are required")
            }

            quizQuestion.explanation.isBlank() -> {
                ValidationResult.Invalid(reason = "Explanation input is required")
            }

            quizQuestion.topicCode <= 0 -> {
                ValidationResult.Invalid(reason = "Topic code must be greater than zero")
            }

            else -> {
                ValidationResult.Valid
            }
        }
    }
}