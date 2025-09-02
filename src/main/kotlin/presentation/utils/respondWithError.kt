package com.simbiri.presentation.utils

import com.simbiri.domain.util.DataError
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

suspend fun RoutingContext.respondWithError(error: DataError) {
    when(error) {
        DataError.DatabaseError -> {
            call.respond(
                message = "An unknown database error occurred",
                status = HttpStatusCode.InternalServerError
            )
        }
        DataError.NotFound -> {
            call.respond(
                message = "The specified resource with the id was not found",
                status = HttpStatusCode.NotFound
            )
        }
        DataError.UnknownError -> {
            call.respond(
                message = "An unknown error occurred",
                status = HttpStatusCode.InternalServerError
            )
        }
        DataError.ValidationError -> {
            call.respond(
                message = "Invalid data provided",
                status = HttpStatusCode.BadRequest
            )
        }
    }
}