package ru.esstu.dev

import io.ktor.server.application.*
import ru.esstu.dev.db.DatabaseFactory

import ru.esstu.dev.plugins.*


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init(environment.config)

    sessions()
    authentication()

    configureSerialization()
    configureRouting()

}
