package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.presentation.config.quizQuestionsList
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.upsertQuizQuestion() { // post (insert) and put/patch (update) to the db can often be one operation
    post(path = "/quiz/questions"){
        val questionRec = call.receive<QuizQuestion>()
        quizQuestionsList.add(questionRec)
        call.respondText("Question added successfully and added to the list in server")

    }
}