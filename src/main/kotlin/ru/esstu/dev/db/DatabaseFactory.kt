package ru.esstu.dev.db

import io.ktor.server.config.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import ru.esstu.dev.db.DatabaseFactory.dbQuery

data class Article(val id: Int, val title: String, val body: String)

object Articles : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcURL = config.property("storage.jdbcURL").getString()
        val user =config.property("storage.username").getString()
        val password = config.property("storage.password").getString()
        val database = Database.connect(jdbcURL, driverClassName,
            user=user,
            password = password)

        database
        transaction(database) {
            SchemaUtils.create(Articles)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}


class DAOFacadeImpl {
    private fun resultRowToArticle(row: ResultRow) = Article(
        id = row[Articles.id],
        title = row[Articles.title],
        body = row[Articles.body],
    )

     suspend fun allArticles(): List<Article> = dbQuery {
        val a = Articles.selectAll().map(::resultRowToArticle)
         a
         Articles.selectAll().map(::resultRowToArticle)
    }
}

val dao: DAOFacadeImpl = DAOFacadeImpl()