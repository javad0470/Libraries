package com.example.db.barbers

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.barberRoute() {

    val barberService: BarberService by inject()

    post("barber/add") {
        val barber = call.receive<Barber>()
        barberService.addBarber(barber = barber)
        call.respond(
            message = "Successfully Added",
            status = HttpStatusCode.Accepted
        )
    }


    get("barber/getAll") {
        val barbers = barberService.getAllBarbers()
        call.respond(
            message = barbers,
            status = HttpStatusCode.OK
        )
    }

    post("barber/update") {
        val barber = call.receive<Barber>()
        barberService.updateBarber(barber = barber)
        call.respond(
            message = "Successfully Updated",
            status = HttpStatusCode.OK
        )
    }

    get("barber/specific") {
        val barberId = call.request.queryParameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val message = barberService.showSpecificItem(barberId = barberId)
        call.respond(
            message = message,
            status = HttpStatusCode.OK
        )
    }

}