package com.simbiri.presentation.routes.quiz_question

import com.simbiri.presentation.config.quizQuestionsList
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

fun Route.deleteQuizQuestionById() {
    delete(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"] // We use a request param to get a specific question with specific id
        if (id.isNullOrBlank()) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Question ID is required")
            return@delete
        }
        //removeIf returns a boolean if object is found and deleted
        val isDeleted = quizQuestionsList.removeIf { it.id == id }
        // for successful deletes we usually return no content and the code 204
        if (isDeleted) call.respond(message = "Question removed successfully", status = HttpStatusCode.NoContent)
        else call.respond(message = "Question not found", status = HttpStatusCode.NotFound)
    }
}