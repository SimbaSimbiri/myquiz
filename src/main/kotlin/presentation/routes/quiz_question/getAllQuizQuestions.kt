package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.presentation.config.quizQuestionsList
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions(){
    get(path = "/quiz/questions") {
        call.respond(quizQuestionsList)
    }
}