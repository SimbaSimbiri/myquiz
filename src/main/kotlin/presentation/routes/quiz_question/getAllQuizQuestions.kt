package com.simbiri.presentation.routes.quiz_question

import com.simbiri.domain.model.QuizQuestion
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions(){
    get(path = "/quiz/questions") {
        val question = QuizQuestion(
            question = "Which day did the 24 stock brokers initiate the buttonwood agreement",
            correctAnswer = "May 17th",
            incorrectAnswers = listOf("June 25th", "Dec 24th", "May 10th"),
            explanation = "The agreement organized securities trading in New York City and was signed on May 17, 1792 between 24 stockbrokers outside of 68 Wall Street.",
            topicCode = 1
        )
        call.respondText(question.toString())

    }
}