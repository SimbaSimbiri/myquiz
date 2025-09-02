package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.repository.QuizQuestionRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuizQuestionById(quizRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"]
        if (id.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Question ID is required")
            return@get
        }
        val question = quizRepository.getQuestionById(id)

        if (question != null) {
            call.respond(message = question, status = HttpStatusCode.OK)
        } else{
            call.respond(message = "Question not found in memory", status= HttpStatusCode.NotFound)
        }
    }
}