package com.simbiri.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.root(){
    get(path = "/") {
        call.respondText("Welcome to Simbiri's Quiz API!")
    }
}
