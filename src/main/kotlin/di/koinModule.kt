package com.simbiri.di

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.DatabaseFactory
import com.simbiri.data.repository.IssueReportRepositoryImpl
import com.simbiri.data.repository.QuizQuestionRepositoryImpl
import com.simbiri.data.repository.QuizTopicRepositoryImpl
import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.repository.QuizQuestionRepository
import com.simbiri.domain.repository.QuizTopicRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {
    single <MongoDatabase> { DatabaseFactory.create() }
    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()
}