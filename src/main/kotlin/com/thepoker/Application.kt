package com.thepoker

import io.ktor.server.application.*
import com.thepoker.plugins.*
import configureSockets
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        // ...
    }.start(wait = true)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    configureSockets()
    configureTemplating()
}
