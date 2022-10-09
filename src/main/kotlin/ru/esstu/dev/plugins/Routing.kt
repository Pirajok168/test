package ru.esstu.dev.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.*
import ru.esstu.dev.db.dao
import ru.esstu.dev.models.PersonSession
import ru.esstu.dev.models.TypeOwner
import ru.esstu.dev.models.UserSession

fun Application.configureRouting() {

    routing {
        get("/login") {
            call.respondHtml {
                body {
                    form(action = "/login", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                        p {
                            +"Username:"
                            textInput(name = "username")
                        }
                        p {
                            +"Password:"
                            passwordInput(name = "password")
                        }
                        p {
                            submitInput() { value = "Login" }
                        }
                    }
                }
            }
        }

        get("/") {
            //call.respond(dao.allArticles())
        }

        authenticate("auth-form") {
            post("/login") {

                call.sessions.set(PersonSession(
                    firstName = "Danila",
                    lastName = "Eremin",
                    patronymic = "Alexandrovich",
                    userId = "12345",
                    userType = "student",
                    owner = TypeOwner.Student("12345"),
                ))

                call.respondRedirect("/hello")
            }
        }

        authenticate("auth-session") {
            get("/hello") {
                val userSession = call.principal<PersonSession>()
                //call.sessions.set(userSession?.copy(count = userSession.count + 1))
                call.respondText("Hello, ${userSession?.firstName}.")
            }
        }

        get("/logout") {
            call.sessions.clear<PersonSession>()
            call.respondRedirect("/login")
        }
    }

}

