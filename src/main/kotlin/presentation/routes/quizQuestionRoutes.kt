package presentation.routes

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository
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
import presentation.routes.path.QuizQuestionRoutesPath

fun Route.quizQuestionRoutes(quizRepository: QuizQuestionRepository) {

    // deleteQuizQuestionById
    delete<QuizQuestionRoutesPath.ById> { path ->

        // We use a request param to get a specific question with specific id
        quizRepository.deleteQuizQuestionById(path.questionId)
            .onSuccess {
                // for successful deletes we usually return no content and the code 204
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }

    // getAllQuizQuestions
    get<QuizQuestionRoutesPath> { path ->
        // we use query params (optional) to filter our results as needed

        quizRepository.getAllQuestions(path.topicCode)
            .onSuccess { questionList ->
                call.respond(message = questionList, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }

    // getQuizQuestionsById
    get<QuizQuestionRoutesPath.ById>{ path ->

        quizRepository.getQuestionById(path.questionId)
            .onSuccess { question ->
                call.respond(message = question, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

    // getRandomQuizQuestion
    get<QuizQuestionRoutesPath.Random> { path ->
        // we use query params (optional) to filter our results as needed

        quizRepository.getRandomQuestions(path.topicCode, path.limit)
            .onSuccess { questionList ->
                call.respond(message = questionList, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }

    //insertQuizQuestionsInBulk
    post<QuizQuestionRoutesPath.Bulk> {
        val quizQuestions = call.receive<List<QuizQuestion>>()
        quizRepository.insertQuestionsInBulk(quizQuestions)
            .onSuccess {
                call.respond(message = "Quiz Questions added in bulk", status = HttpStatusCode.Created)
            }
            .onFailure { error -> respondWithError(error) }


    }

    // upsertQuizQuestion
    // post (insert) and put/patch (update) to the db can often be one operation
    post<QuizQuestionRoutesPath> {

        val questionRec = call.receive<QuizQuestion>()
        quizRepository.upsertQuizQuestion(questionRec)
            .onSuccess {
                call.respond(
                    message = "Question added successfully to the MongoDB quiz collection",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}