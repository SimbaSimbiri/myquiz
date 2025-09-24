package com.simbiri.presentation.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.root(){
    get(path = "/") {
        call.respondText("Welcome to Simbiri's Quiz API!\n Test yourself on matters ML, Trading & Global investments, ICT concepts, Compose, Ktor and much more!")
    }
}
