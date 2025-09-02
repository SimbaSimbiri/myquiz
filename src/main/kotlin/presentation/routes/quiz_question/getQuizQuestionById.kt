package com.simbiri.presentation.routes.quiz_question

import com.simbiri.presentation.config.quizQuestionsList
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getQuizQuestionById() {
    get(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"]
        val question = quizQuestionsList.find{it.id == id}
        if (question != null) {
            call.respond(question)
        }
    }
}