package com.simbiri.presentation.config

import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.presentation.routes.root
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import presentation.routes.issueReportRoutes
import presentation.routes.quizQuestionRoutes
import presentation.routes.quizTopicRoutes

fun Application.configureRouting(){
    // we install the Resource plugin here which we used for type safe routing
    install(Resources)

    val quizRepository : QuizQuestionRepository by inject()
    val topicsRepository : QuizTopicRepository by inject()
    val issueReportRepository : IssueReportRepository by inject()

    // routes tell our server what data to return once a specific endpoint is hit
    // if you have any route in the backend app then all must be defined inside this routing block
    routing {
        // the get block in the below functions exposes the call class instance that will be used to either respond
        // with or receive data depending on whether the request was a get or post
        root()

        //Quiz Questions
        quizQuestionRoutes(quizRepository)

        // Quiz Topics
        quizTopicRoutes(topicsRepository)

        // Issue Reports
        issueReportRoutes(issueReportRepository)

        // static route for our image topics
        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }

}
