package com.example.routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import sun.rmi.runtime.Log

fun Route.insertDataIntoServicesTable() = transaction {

    post("/getBody") {
        val title = call.receive<Services>()
        println("bodyContent: ${title.title}")
        println("bodyContent: ${title.description}")
        call.respond(
            message = "OK",
            status = HttpStatusCode.OK
        )
    }

}

object ServicesTable : Table() {
    private val id = integer("id")
    val store_id = integer("store_id")
    val title = varchar("title", length = 50)
    val fee = varchar("fee", length = 50)
    val duration = varchar("duration", length = 50)

    override val primaryKey = PrimaryKey(id, name = "service_id")
}

@Serializable
data class Services(
    var title: String,
    var description: String
)