package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.upsertQuizQuestion(quizRepository : QuizQuestionRepository) { // post (insert) and put/patch (update) to the db can often be one operation
    post(path = "/quiz/questions") {
        val questionRec = call.receive<QuizQuestion>()
        quizRepository.upsertQuizQuestion(questionRec)
        call.respond(
            message = "Question added successfully and server memory updated",
            status = HttpStatusCode.Created
        )

    }
}