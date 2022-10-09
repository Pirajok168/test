package ru.esstu.dev.plugins
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import ru.esstu.dev.models.PersonSession
import ru.esstu.dev.models.UserSession


fun Application.authentication() {
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                if (credentials.name == "jetbrains" && credentials.password == "foobar") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
        session<PersonSession>("auth-session") {
            validate { session ->
                if(session.firstName == "Danila") {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
    }
}