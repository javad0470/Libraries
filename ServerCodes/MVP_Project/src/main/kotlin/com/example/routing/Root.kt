package com.example.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rootRoute(){
    get("/") {
        call.respondText("Hello World!")
    }
}