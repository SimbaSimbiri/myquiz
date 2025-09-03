package com.simbiri.presentation.routes.quiz_topics

import com.simbiri.domain.model.QuizTopic
import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.resources.post
import io.ktor.server.response.respond

fun Route.upsertQuizTopic(quizTopicRepository: QuizTopicRepository) {
    post<QuizTopicRoutesPath> {
        val quizTopicRec = call.receive<QuizTopic>()

        quizTopicRepository.upsertTopic(quizTopicRec)
            .onSuccess {
                call.respond(
                    message = "New topic was uploaded to the MongoDB collection",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}