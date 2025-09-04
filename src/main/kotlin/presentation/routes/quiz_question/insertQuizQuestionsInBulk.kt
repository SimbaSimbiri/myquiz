package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.insertQuestionsInBulk(quizRepository: QuizQuestionRepository) {
    post<QuizQuestionRoutesPath.Bulk> {
        val quizQuestions = call.receive<List<QuizQuestion>>()
        quizRepository.insertQuestionsInBulk(quizQuestions)
            .onSuccess {
                call.respond(message = "Quiz Questions added in bulk", status = HttpStatusCode.Created)
            }
            .onFailure { error -> respondWithError(error) }


    }
}