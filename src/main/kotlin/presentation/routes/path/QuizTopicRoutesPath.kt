package presentation.routes.path

import io.ktor.resources.Resource

@Resource("/quiz/topics")
class QuizTopicRoutesPath {
    @Resource("{topicId}")
    data class ById(
        val parent: QuizTopicRoutesPath = QuizTopicRoutesPath(),
        val topicId : String?
    )
}