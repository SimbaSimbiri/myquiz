package com.simbiri.presentation.config

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.DatabaseFactory
import com.simbiri.data.repository.QuizQuestionRepositoryImpl
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.presentation.routes.quiz_question.deleteQuizQuestionById
import com.simbiri.presentation.routes.quiz_question.getAllQuizQuestions
import com.simbiri.presentation.routes.quiz_question.getQuizQuestionById
import com.simbiri.presentation.routes.quiz_question.upsertQuizQuestion
import com.simbiri.presentation.routes.root
import io.ktor.server.application.*
import io.ktor.server.routing.*
import javax.xml.crypto.Data

fun Application.configureRouting(){
    val mongoDatabase: MongoDatabase  = DatabaseFactory.create()
    val quizRepository : QuizQuestionRepository = QuizQuestionRepositoryImpl(mongoDatabase)
    // routes tell our server what data to return once a specific endpoint is hit
    // if you have any route in the backend app then all mus be defined inside this routing block
    routing {
        // the get block in the below functions exposes the call class instance that will be used to either respond
        // with or receive data depending on whether the request was a get or post
        root()
        getAllQuizQuestions(quizRepository)
        upsertQuizQuestion(quizRepository)
        deleteQuizQuestionById(quizRepository)
        getQuizQuestionById(quizRepository)
    }

}
