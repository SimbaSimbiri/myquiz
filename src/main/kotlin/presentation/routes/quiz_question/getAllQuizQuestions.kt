package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllQuizQuestions(quizRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions") {

        // we use query params (optional) to filter our results as needed
        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull()

        quizRepository.getAllQuestions(topicCode, limit)
            .onSuccess { questions ->
                call.respond(message = questions, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}