package com.simbiri.data.repository

import com.simbiri.domain.model.QuizQuestion
import com.simbiri.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl : QuizQuestionRepository {
    // class to modularize all the crud operation and leave routes to deal with requests, queries, status codes and
    // responses
    private val quizQuestionsList = mutableListOf<QuizQuestion>()

    override fun upsertQuizQuestion(questionRec: QuizQuestion) {
        quizQuestionsList.add(questionRec)
    }

    override fun getAllQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
        // if we have valid topicCode filter by it, else only use the limit filter
        // if limit is null just return the whole list
        return  if (topicCode != null) {
            quizQuestionsList.
            filter{ it.topicCode == topicCode }.
            take(limit ?: quizQuestionsList.size)
        } else {
            quizQuestionsList.take(limit ?: quizQuestionsList.size)
        }
    }

    override fun getQuestionById(questionId: String): QuizQuestion? {
        return quizQuestionsList.find { it.id == questionId }
    }


    override fun deleteQuizQuestionById(questionId: String): Boolean {
        // removeIf returns a boolean if object is found and deleted
        return quizQuestionsList.removeIf { it.id == questionId }
    }
}