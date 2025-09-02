package com.simbiri.presentation.routes.quiz_question

import com.simbiri.presentation.config.quizQuestionsList
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

fun Route.deleteQuizQuestionById() {
    delete(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"] // We use a request param to get a specific question with specific id
        quizQuestionsList.removeIf {it.id == id}
        call.respondText("Quiz question $id deleted successfully")
    }
}