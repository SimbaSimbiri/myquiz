package com.simbiri.presentation.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.root(){
    get(path = "/") {
        call.respondText("Welcome to Simbiri's Quiz API!")
    }
}
