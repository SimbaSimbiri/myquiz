package com.simbiri.domain.repository

import com.simbiri.domain.model.QuizTopic
import com.simbiri.domain.util.DataError
import com.simbiri.domain.util.ResultType

interface QuizTopicRepository {

    suspend fun getAllTopics(): ResultType<List<QuizTopic>, DataError>
    suspend fun upsertTopic(topic: QuizTopic): ResultType<Unit, DataError>
    suspend fun getTopicById(topicId : String?): ResultType<QuizTopic, DataError>
    suspend fun deleteTopicById(topicId : String?): ResultType<Unit, DataError>
}
