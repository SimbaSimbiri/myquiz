package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions(quizRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions") {

        // we use query params (optional) to filter our results as needed
        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull()
        val filteredResult  = quizRepository.getAllQuestions(topicCode, limit)

        if (filteredResult.isNotEmpty()) {
            call.respond(message = filteredResult, status = HttpStatusCode.OK)
        } else {
            call.respond(message = "Questions not found with the specified params", status = HttpStatusCode.NotFound)
        }
    }
}