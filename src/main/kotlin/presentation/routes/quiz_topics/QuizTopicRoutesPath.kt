package com.simbiri.presentation.routes.quiz_topics

import io.ktor.resources.Resource

@Resource("/quiz/topics")
class QuizTopicRoutesPath {
    @Resource("{topicId}")
    data class ById(
        val parent: QuizTopicRoutesPath = QuizTopicRoutesPath(),
        val topicId : String?
    )
}