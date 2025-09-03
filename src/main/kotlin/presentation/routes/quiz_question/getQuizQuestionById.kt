package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuizQuestionById(quizRepository: QuizQuestionRepository) {
    get<QuizQuestionRoutesPath.ById>{path ->

        quizRepository.getQuestionById(path.questionId)
            .onSuccess { question ->
                call.respond(message = question, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}