package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.presentation.config.quizQuestionsList
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions() {
    get(path = "/quiz/questions") {

        // we use query params (optional) to filter our results as needed
        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull()
        // if we have valid topicCode filter by it, else only use the limit filter
        // if limit is null just return the whole list
        val filteredResult = if (topicCode != null) {
            quizQuestionsList.
            filter{ it.topicCode == topicCode }.
            take(limit ?: quizQuestionsList.size)
        } else {
            quizQuestionsList.take(limit ?: quizQuestionsList.size)
        }

        if (filteredResult.isNotEmpty()) {
            call.respond(message = filteredResult, status = HttpStatusCode.OK)
        } else {
            call.respond(message = "Questions not found with the specified params", status = HttpStatusCode.NotFound)
        }
    }
}