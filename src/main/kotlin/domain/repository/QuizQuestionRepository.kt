package com.simbiri.domain.repository

import com.simbiri.domain.model.QuizQuestion

interface QuizQuestionRepository {
    suspend fun upsertQuizQuestion(questionRec: QuizQuestion)
    suspend fun getAllQuestions(topicCode: Int?, limit: Int?) : List<QuizQuestion>
    suspend fun getQuestionById(questionId: String): QuizQuestion?
    suspend fun deleteQuizQuestionById(questionId: String) : Boolean
}