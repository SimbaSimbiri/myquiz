package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.upsertQuizQuestion(quizRepository : QuizQuestionRepository) {
    // post (insert) and put/patch (update) to the db can often be one operation
    post(path = "/quiz/questions") {
        val questionRec = call.receive<QuizQuestion>()
        quizRepository.upsertQuizQuestion(questionRec)
            .onSuccess {
                call.respond(
                    message = "Question added successfully to the MongoDB quiz collection",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure {error ->
                respondWithError(error)
            }

    }
}