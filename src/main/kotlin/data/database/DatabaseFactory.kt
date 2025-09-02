package com.simbiri.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.util.Constants.MONGO_DB_NAME
import com.simbiri.data.util.Constants.MONGO_URL

object DatabaseFactory {
    // here we will create our MongoDB object

    fun create(): MongoDatabase {
        val connectionString =
            System.getenv(MONGO_URL) ?: throw IllegalArgumentException("Mongo URL not set in environment")
        // we connect to our db using the MongoClient and connection str
        val mongoClient = MongoClient.create(connectionString)

        return mongoClient.getDatabase(MONGO_DB_NAME)
    }
}