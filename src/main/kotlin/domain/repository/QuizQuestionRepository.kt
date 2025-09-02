package com.simbiri.domain.repository

import com.simbiri.domain.model.QuizQuestion

interface QuizQuestionRepository {
    fun upsertQuizQuestion(questionRec: QuizQuestion)
    fun getAllQuestions(topicCode: Int?, limit: Int?) : List<QuizQuestion>
    fun getQuestionById(questionId: String): QuizQuestion?
    fun deleteQuizQuestionById(questionId: String) : Boolean
}