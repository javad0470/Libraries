package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

//fun main(args:Array<String>) : Unit = io.ktor.server.netty.EngineMain.main(args)

fun main(){
    embeddedServer(Netty,port = 8080, watchPaths = listOf("classes","resources")){
        module()
    }.start(wait = true)
}


fun Application.module() {
    install(CallLogging)
    routing {
        get("/") {
            call.respondText("Hello, Worlds")
        }
    }

}