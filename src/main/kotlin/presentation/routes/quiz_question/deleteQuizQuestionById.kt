package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.delete
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.deleteQuizQuestionById(quizRepository: QuizQuestionRepository) {
    delete<QuizQuestionRoutesPath.ById> { path ->

        // We use a request param to get a specific question with specific id
        quizRepository.deleteQuizQuestionById(path.questionId)
            .onSuccess {
                // for successful deletes we usually return no content and the code 204
                call.respond(
                    message = "Question with id ${path.questionId} removed successfully",
                    status = HttpStatusCode.NoContent
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}