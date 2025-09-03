package com.simbiri.presentation.routes.quiz_question

import io.ktor.resources.*


@Resource("/quiz/questions")
class QuizQuestionRoutesPath(val topicCode: Int? = null, val limit: Int? = null) {
    //for request params which is part of the url we have to extend a new inner data class as a resource
    // the path name/request param should be the same as the attribute of the inner data class
    @Resource("/{questionId}")
    data class ById(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath(),
        val questionId: String
    )
}