package com.simbiri.presentation.routes.quiz_topics

import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.getQuizTopicById(quizTopicRepository: QuizTopicRepository) {
    get<QuizTopicRoutesPath.ById> { path ->

        quizTopicRepository.getTopicById(path.topicId)
            .onSuccess {quizTopic ->
                call.respond(
                message = quizTopic, status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}