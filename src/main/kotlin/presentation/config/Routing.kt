package com.simbiri.presentation.config

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.DatabaseFactory
import com.simbiri.data.repository.IssueReportRepositoryImpl
import com.simbiri.data.repository.QuizQuestionRepositoryImpl
import com.simbiri.data.repository.QuizTopicRepositoryImpl
import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.presentation.routes.issue_reports.deleteIssueReportById
import com.simbiri.presentation.routes.issue_reports.getAllIssueReports
import com.simbiri.presentation.routes.issue_reports.insertIssueReport
import com.simbiri.presentation.routes.quiz_question.deleteQuizQuestionById
import com.simbiri.presentation.routes.quiz_question.getAllQuizQuestions
import com.simbiri.presentation.routes.quiz_question.getQuizQuestionById
import com.simbiri.presentation.routes.quiz_question.upsertQuizQuestion
import com.simbiri.presentation.routes.quiz_topics.deleteQuizTopicById
import com.simbiri.presentation.routes.quiz_topics.getAllQuizTopics
import com.simbiri.presentation.routes.quiz_topics.getQuizTopicById
import com.simbiri.presentation.routes.quiz_topics.upsertQuizTopic
import com.simbiri.presentation.routes.root
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureRouting(){
    // we install the Resource plugin here which we used for type safe routing
    install(Resources)

    val mongoDatabase: MongoDatabase  = DatabaseFactory.create()
    val quizRepository : QuizQuestionRepository = QuizQuestionRepositoryImpl(mongoDatabase)
    val topicsRepository : QuizTopicRepository = QuizTopicRepositoryImpl(mongoDatabase)
    val issueReportRepository : IssueReportRepository = IssueReportRepositoryImpl(mongoDatabase)

    // routes tell our server what data to return once a specific endpoint is hit
    // if you have any route in the backend app then all mus be defined inside this routing block
    routing {
        // the get block in the below functions exposes the call class instance that will be used to either respond
        // with or receive data depending on whether the request was a get or post
        root()

        //Quiz Questions
        getAllQuizQuestions(quizRepository)
        upsertQuizQuestion(quizRepository)
        deleteQuizQuestionById(quizRepository)
        getQuizQuestionById(quizRepository)

        // Quiz Topics
        getAllQuizTopics(topicsRepository)
        upsertQuizTopic(topicsRepository)
        deleteQuizTopicById(topicsRepository)
        getQuizTopicById(topicsRepository)

        // Issue Reports
        getAllIssueReports(issueReportRepository)
        insertIssueReport(issueReportRepository)
        deleteIssueReportById(issueReportRepository)

        // static route for our image topics
        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )



    }

}
