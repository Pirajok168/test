package ru.esstu.dev.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import ru.esstu.dev.models.PersonSession
import ru.esstu.dev.models.UserSession

fun Application.sessions() {
    install(Sessions) {
        cookie<PersonSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60
        }
    }
}