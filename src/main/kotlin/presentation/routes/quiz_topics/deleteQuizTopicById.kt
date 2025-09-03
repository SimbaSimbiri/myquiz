package com.simbiri.presentation.routes.quiz_topics

import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.deleteQuizTopicById(quizTopicRepository: QuizTopicRepository) {
    delete<QuizTopicRoutesPath.ById> {path->
        quizTopicRepository.deleteTopicById(path.topicId)
            .onSuccess {
                call.respond(message = "Quiz Topic Deleted Successfully", status= HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

}