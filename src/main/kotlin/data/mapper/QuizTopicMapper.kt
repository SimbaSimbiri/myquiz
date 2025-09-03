package com.simbiri.data.mapper

import com.simbiri.data.database.entity.QuizTopicEntity
import com.simbiri.domain.model.QuizTopic

fun QuizTopicEntity.toQuizTopic(): QuizTopic = QuizTopic(
    id = _id,
    name = name,
    imageUrl = imageUrl,
    code = code
)

fun QuizTopic.toQuizTopicEntity(): QuizTopicEntity = QuizTopicEntity(
    name = name,
    imageUrl = imageUrl,
    code = code
)