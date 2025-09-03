package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllQuizQuestions(quizRepository: QuizQuestionRepository) {
    get<QuizQuestionRoutesPath> { path ->
        // we use query params (optional) to filter our results as needed

        quizRepository.getAllQuestions(path.topicCode, path.limit)
            .onSuccess { questionList ->
                call.respond(message = questionList, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}