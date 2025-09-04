package com.simbiri.domain.repository

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.util.DataError
import com.simbiri.domain.util.ResultType

interface QuizQuestionRepository {
    suspend fun upsertQuizQuestion(questionRec: QuizQuestion) : ResultType<Unit, DataError>
    suspend fun getAllQuestions(topicCode: Int?) : ResultType<List<QuizQuestion>, DataError>
    suspend fun getRandomQuestions(topicCode: Int?, limit: Int?) : ResultType<List<QuizQuestion>, DataError>

    suspend fun getQuestionById(questionId: String?): ResultType<QuizQuestion, DataError>

    suspend fun insertQuestionsInBulk(questions: List<QuizQuestion>): ResultType<Unit, DataError>
    suspend fun deleteQuizQuestionById(questionId: String?) : ResultType<Unit, DataError>
}