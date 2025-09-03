package com.simbiri.presentation.routes.quiz_topics

import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllQuizTopics(quizTopicRepository: QuizTopicRepository) {
    get<QuizTopicRoutesPath> {
        quizTopicRepository.getAllTopics()
            .onSuccess { topics ->
                call.respond(message = topics, status = HttpStatusCode.OK)
            }
            .onFailure {error ->
                respondWithError(error)
            }

    }

}