package com.simbiri.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.entity.QuizTopicEntity
import com.simbiri.data.mapper.toQuizTopic
import com.simbiri.data.mapper.toQuizTopicEntity
import com.simbiri.data.util.Constants.TOPIC_COLLECTION
import com.simbiri.domain.model.QuizTopic
import com.simbiri.domain.repository.QuizTopicRepository
import com.simbiri.domain.util.DataError
import com.simbiri.domain.util.ResultType
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class QuizTopicRepositoryImpl(mongoDatabase: MongoDatabase) : QuizTopicRepository {
    private val topicsCollection = mongoDatabase.getCollection<QuizTopicEntity>(TOPIC_COLLECTION)

    override suspend fun getAllTopics(): ResultType<List<QuizTopic>, DataError> {
        return try {
            val sortQuery = Sorts.ascending(QuizTopicEntity::code.name)

            val topics = topicsCollection
                .find()
                .sort(sortQuery)
                .map { it.toQuizTopic() }
                .toList()

            if (topics.isNotEmpty()) {
                ResultType.Success(topics)
            } else {
                ResultType.Failure(DataError.NotFound)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun upsertTopic(topic: QuizTopic): ResultType<Unit, DataError> {
        return try {
            if (topic.id == null) {
                topicsCollection.insertOne(topic.toQuizTopicEntity())
            } else{
                val filterQuery = Filters.eq(QuizTopicEntity::_id.name, topic.id)
                val updateCombine = Updates.combine(
                    Updates.set(QuizTopicEntity::name.name, topic.name),
                    Updates.set(QuizTopicEntity::imageUrl.name, topic.imageUrl),
                    Updates.set(QuizTopicEntity::code.name, topic.code)
                )
                val updatedResult = topicsCollection.updateOne(filterQuery, updateCombine)

                if (updatedResult.matchedCount == 0L) {
                   return ResultType.Failure(DataError.NotFound)
                }
            }
            ResultType.Success(Unit)

        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun getTopicById(topicId: String?): ResultType<QuizTopic, DataError> {
        if (topicId.isNullOrBlank()) {
            return ResultType.Failure(DataError.ValidationError)
        }
        return try {
            val filterQuery = Filters.eq(QuizTopicEntity::_id.name, topicId)
            val topic = topicsCollection.find(filterQuery).firstOrNull()

            if (topic == null) {
                ResultType.Failure(DataError.NotFound)
            } else  {
                ResultType.Success(topic.toQuizTopic())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun deleteTopicById(topicId: String?): ResultType<Unit, DataError> {
        if (topicId.isNullOrBlank()) {
            return ResultType.Failure(DataError.ValidationError)
        }
        return try {
            val filterQuery = Filters.eq(QuizTopicEntity::_id.name, topicId)
            val deletedResult = topicsCollection.deleteOne(filterQuery)

            if (deletedResult.deletedCount > 0L) {
                ResultType.Success(Unit)
            } else {
                ResultType.Failure(DataError.NotFound)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }
}