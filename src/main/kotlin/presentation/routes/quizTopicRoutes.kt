package presentation.routes

import com.simbiri.domain.model.QuizTopic
import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import presentation.routes.path.QuizTopicRoutesPath

fun Route.quizTopicRoutes(quizTopicRepository: QuizTopicRepository) {
    // delete QuizTopic ById
    delete<QuizTopicRoutesPath.ById> { path->
        quizTopicRepository.deleteTopicById(path.topicId)
            .onSuccess {
                call.respond(message = "Quiz Topic Deleted Successfully", status= HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

    // getAllQuizTopics
    get<QuizTopicRoutesPath> {
        quizTopicRepository.getAllTopics()
            .onSuccess { topics ->
                call.respond(message = topics, status = HttpStatusCode.OK)
            }
            .onFailure {error ->
                respondWithError(error)
            }

    }

    // getQuizTopicById
    get<QuizTopicRoutesPath.ById> { path ->

        quizTopicRepository.getTopicById(path.topicId)
            .onSuccess {quizTopic ->
                call.respond(
                    message = quizTopic, status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

    // upsertQuizTopic
    post<QuizTopicRoutesPath> {
        val quizTopicRec = call.receive<QuizTopic>()

        quizTopicRepository.upsertTopic(quizTopicRec)
            .onSuccess {
                call.respond(
                    message = "New topic was uploaded to the MongoDB collection",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }



}