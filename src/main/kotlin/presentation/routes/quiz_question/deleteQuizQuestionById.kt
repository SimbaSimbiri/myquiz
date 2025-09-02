package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteQuizQuestionById(quizRepository: QuizQuestionRepository) {
    delete(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"] // We use a request param to get a specific question with specific id

        quizRepository.deleteQuizQuestionById(id)
            .onSuccess { question ->
                // for successful deletes we usually return no content and the code 204
                call.respond(message = "Question removed successfully", status = HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}