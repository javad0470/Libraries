package com.example.db.items

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object BarberShopTable : IntIdTable() {
    // val id = integer("id").autoIncrement()
    val title = varchar("title", length = 256)
    val image = varchar("image", length = 500)
    val address = varchar("address", length = 256)
    val rate = varchar("rate", length = 10)
    val services = varchar("services", length = 256)
    val description = varchar("description", length = 256)
    val details = varchar("details", length = 256)
    val date_plan = varchar("date_plan", length = 256)
    val province = varchar("province", length = 50)
    val city = varchar("city", length = 50)

    //override val primaryKey = PrimaryKey(id, name = "items_id")
}

class BarberShopEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BarberShopEntity>(BarberShopTable)

    var title by BarberShopTable.title
    var image by BarberShopTable.image
    var address by BarberShopTable.address
    var rate by BarberShopTable.rate
    var services by BarberShopTable.services
    var description by BarberShopTable.description
    var details by BarberShopTable.details
    var datePlan by BarberShopTable.date_plan
    var province by BarberShopTable.province
    var city by BarberShopTable.city

    override fun toString(): String =
        "BarberShop($title,$image,$address,$address,$rate,$services,$description,$details,$datePlan,$province,$city)"

    fun toBarberShop() = BarberShop(
        id = id.value,
        title = title,
        image = image,
        address = address,
        rate = rate,
        services = services,
        description = description,
        details = details,
        date_plan = datePlan,
        province = province,
        city = city
    )
}

@Serializable
data class BarberShop(
    val id: Int,
    val title: String,
    val image: String,
    val address: String,
    val rate: String,
    val services: String,
    val description: String,
    val details: String,
    val date_plan: String,
    val province: String,
    val city: String,
)
