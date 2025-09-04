package presentation.routes.path

import io.ktor.resources.Resource

@Resource("/quiz/questions")
class QuizQuestionRoutesPath(val topicCode: Int? = null) {
    //for request params which is part of the url we have to extend a new inner data class as a resource
    // the path name/request param should be the same as the attribute of the inner data class
    @Resource("{questionId}")
    data class ById(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath(),
        val questionId: String
    )

    // We add path to add questions in bulk
    @Resource("bulk")
    data class Bulk(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath(),
    )

    @Resource("random")
    data class Random(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath(),
        val topicCode: Int? = null,
        val limit: Int? = null
    )
}