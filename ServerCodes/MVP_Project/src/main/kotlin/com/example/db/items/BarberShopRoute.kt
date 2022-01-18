package com.example.db.items

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.barberShopRoute() {

    val barberShopService: BarberShopService by inject()

    get("barberShop/getAll") {
        val items = barberShopService.getAllBarberShops()
        call.respond(
            message = items,
            status = HttpStatusCode.OK
        )
    }

    post("barberShop/add"){
        val itemResult = call.receive<BarberShop>()
        barberShopService.addBarberShop(item = itemResult)
        call.respond(
            message = "Successfully Added",
            status = HttpStatusCode.Accepted
        )
    }
}