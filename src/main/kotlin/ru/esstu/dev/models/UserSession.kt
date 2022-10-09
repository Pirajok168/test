package ru.esstu.dev.models

import io.ktor.server.auth.*

data class UserSession(val name: String, val count: Int) : Principal



data class PersonSession(
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val userId: String,
    val userType: String,
    val owner: TypeOwner
): Principal


sealed class TypeOwner{
    data class Teacher(val id: String): TypeOwner()
    data class Student(val id: String): TypeOwner()
}