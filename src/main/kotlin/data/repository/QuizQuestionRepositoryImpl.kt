package com.simbiri.data.repository

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.entity.QuizQuestionEntity
import com.simbiri.data.database.mapper.toQuizQuestion
import com.simbiri.data.database.mapper.toQuizQuestionEntity
import com.simbiri.data.util.Constants.QUESTION_COLLECTION
import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.util.DataError
import com.simbiri.domain.util.ResultType
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson
import kotlin.collections.emptyList

class QuizQuestionRepositoryImpl(mongoDatabase: MongoDatabase) : QuizQuestionRepository {
    // class to modularize all the crud operation and leave routes to deal with requests, queries, status codes and
    // responses

    // we will use the collection from our db to store our quiz_questions and in our crud ops
    private val questionCollection = mongoDatabase.getCollection<QuizQuestionEntity>(QUESTION_COLLECTION)

    override suspend fun upsertQuizQuestion(questionRec: QuizQuestion): ResultType<Unit, DataError> {
        return try {
            // if the questionRec provided lacks an id, we know we are uploading it for the first time
            if (questionRec.id == null) {
                questionCollection.insertOne(questionRec.toQuizQuestionEntity())
            } else {
                val filterQuery = Filters.eq(QuizQuestionEntity::_id.name, questionRec.id)
                // we use our update combine query to update all the fields of the document found after filtering
                val updateQuery = Updates.combine(
                    Updates.set(QuizQuestionEntity::question.name, questionRec.question),
                    Updates.set(QuizQuestionEntity::correctAnswer.name, questionRec.correctAnswer),
                    Updates.set(QuizQuestionEntity::incorrectAnswers.name, questionRec.incorrectAnswers),
                    Updates.set(QuizQuestionEntity::explanation.name, questionRec.explanation),
                    Updates.set(QuizQuestionEntity::topicCode.name, questionRec.topicCode),
                )
                val updatedResult = questionCollection.updateOne(filter = filterQuery, update = updateQuery)
                // if no match was found, hence nothing updated, return not found error
                if (updatedResult.matchedCount == 0L) {
                    return ResultType.Failure(DataError.NotFound)
                }
            }
            // if the code reached here, successful update/upload was done and no DataError was returned
            ResultType.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun getAllQuestions(topicCode: Int?): ResultType<List<QuizQuestion>, DataError> {
        // if we have valid topicCode filter by it
        return try {
            val filterQuery = topicCode?.let {it ->
                Filters.eq(QuizQuestionEntity::topicCode.name, it)
            } ?: Filters.empty() // if topicCode is null we don't need to filter anything

            val questions = questionCollection
                .find(filter = filterQuery)
                .map { it.toQuizQuestion() }
                .toList()

            if (questions.isNotEmpty()) ResultType.Success(questions)
            else ResultType.Failure(DataError.NotFound)

        } catch (e: Exception) {
            // this caught exception can only be due to an internal MongoDB error
            // that occurred when doing the filterQuery or when mapping them to list
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun getRandomQuestions(topicCode: Int?,limit: Int?): ResultType<List<QuizQuestion>, DataError> {
        return try {
            // if the limit doesn't satisfy the predicate it becomes null,
            // then we assign the value to the right of the elvis
            val questionLimit = limit?.takeIf { it > 0 } ?: 10
            val filterQuery = Filters.eq(
                QuizQuestionEntity::topicCode.name, topicCode
            )
            val matchStage = if (topicCode == null || topicCode == 0){
                emptyList<Bson>()
            } else{
                listOf(Aggregates.match(filterQuery))
            }
            val pipeline =  matchStage + Aggregates.sample(questionLimit)

            val questions = questionCollection
                .aggregate(pipeline)
                .map { it.toQuizQuestion() }
                .toList()

            if (questions.isNotEmpty()) ResultType.Success(questions)
            else ResultType.Failure(DataError.NotFound)

        } catch (e: Exception) {
            // this caught exception can only be due to an internal MongoDB error
            // that occurred when doing the filterQuery or when mapping them to list
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun getQuestionById(questionId: String?): ResultType<QuizQuestion, DataError> {
        // we first check for the validity of the 'questionId' param passed in
        if (questionId.isNullOrBlank()) {
            return ResultType.Failure(DataError.ValidationError)
        }

        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, questionId
            )
            // our filtered result will be a list so we just extract the first item, or get null if empty
            val questionEntity = questionCollection.find(filter = filterQuery).firstOrNull()
            // find can result in an empty result hence we need to check the result type
            if (questionEntity != null) {
                val question = questionEntity.toQuizQuestion()
                ResultType.Success(question)
            } else {
                ResultType.Failure(DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun insertQuestionsInBulk(questions: List<QuizQuestion>): ResultType<Unit, DataError> {
        return try {
            val questionEntityList = questions.map { it.toQuizQuestionEntity() }
            questionCollection.insertMany(questionEntityList)
            ResultType.Success(Unit)

        } catch (e: Exception){
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }


    override suspend fun deleteQuizQuestionById(questionId: String?): ResultType<Unit, DataError> {
        // check for request param validation
        if (questionId.isNullOrBlank()) {
            return ResultType.Failure(DataError.ValidationError)
        }
        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, questionId
            )
            val delResult = questionCollection.deleteOne(filter = filterQuery)

            return if (delResult.deletedCount > 0L){
                ResultType.Success(Unit)
            } else {
                ResultType.Failure(DataError.NotFound)
            }
        } catch (e: Exception) {
            // this caught exception can only be due to an internal MongoDB error
            // that occurred when doing the filterQuery and deleting the document
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }
}