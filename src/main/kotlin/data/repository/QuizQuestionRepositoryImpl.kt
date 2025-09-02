package com.simbiri.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.entity.QuizQuestionEntity
import com.simbiri.data.database.mapper.toQuizQuestion
import com.simbiri.data.database.mapper.toQuizQuestionEntity
import com.simbiri.data.util.Constants.QUESTION_COLLECTION
import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlin.collections.emptyList

class QuizQuestionRepositoryImpl(mongoDatabase: MongoDatabase) : QuizQuestionRepository {
    // class to modularize all the crud operation and leave routes to deal with requests, queries, status codes and
    // responses

    // we will use the collection from our db to store our quiz_questions and in our crud ops
    private val questionCollection = mongoDatabase.getCollection<QuizQuestionEntity>(QUESTION_COLLECTION)

    override suspend fun upsertQuizQuestion(questionRec: QuizQuestion) {
        try {
            // if the questionRec provided lacks an id, we know we are uploading it for the first time
            if (questionRec.id == null) {
                questionCollection.insertOne(questionRec.toQuizQuestionEntity())
            } else {
                val filterQuery = Filters.eq(QuizQuestionEntity::_id.name, questionRec.id)
                // we use our update combine query to update all the fields of the document found after filterinb
                val updateQuery = Updates.combine(
                    Updates.set(QuizQuestionEntity::question.name, questionRec.question),
                    Updates.set(QuizQuestionEntity::correctAnswer.name, questionRec.correctAnswer),
                    Updates.set(QuizQuestionEntity::incorrectAnswers.name, questionRec.incorrectAnswers),
                    Updates.set(QuizQuestionEntity::explanation.name, questionRec.explanation),
                    Updates.set(QuizQuestionEntity::topicCode.name, questionRec.topicCode),
                    )
                questionCollection.updateOne(filter= filterQuery, update= updateQuery)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAllQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
        // if we have valid topicCode filter by it, else only use the limit filter
        // if limit is null just return the whole list
        return try {
            val filterQuery = topicCode?.let{
                Filters.eq(QuizQuestionEntity::topicCode.name, it)
            } ?:Filters.empty() // if topicCode is null we don't need to filter anything

            // if the limit is invalid/empty, we assign the value to the right of the elvis
            val questionLimit = limit?.takeIf { it > 0 } ?: 10

            questionCollection
                .find(filter = filterQuery)
                .limit(questionLimit)
                .map{it.toQuizQuestion()}
                .toList()

        } catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getQuestionById(questionId: String): QuizQuestion? {

        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, questionId
            )
            // our filtered result will be a list so we just extract the first item
            val questionEntity = questionCollection.find(filter= filterQuery).firstOrNull()
            questionEntity?.toQuizQuestion()
        } catch (e: Exception) {
          e.printStackTrace()
          null
        }
    }


    override suspend fun deleteQuizQuestionById(questionId: String): Boolean {
        // removeIf returns a boolean if object is found and deleted
        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, questionId
            )
            val delResult = questionCollection.deleteOne(filter= filterQuery)
            delResult.deletedCount  > 0

        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}