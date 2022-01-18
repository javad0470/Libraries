package com.example.db.barbers

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object BarberTable : IntIdTable() {
    val store_id = integer("store_id")
    val firstName = varchar(name = "first_name", length = 256)
    val last_name = varchar(name = "last_name", length = 256)
    val phone = varchar(name = "phone", length = 11)
    val region = integer(name = "region")
}

class BarberEntity(id: EntityID<Int>) : IntEntity(id = id) {
    companion object : IntEntityClass<BarberEntity>(BarberTable)

    var storeId by BarberTable.store_id
    var firstName by BarberTable.firstName
    var lastName by BarberTable.last_name
    var phone by BarberTable.phone
    var region by BarberTable.region

    override fun toString(): String =
        "Barber($storeId,$firstName,$lastName,$phone,$region)"

    fun toBarber() = Barber(
        id = id.value,
        store_id = storeId,
        first_name = firstName,
        last_name = lastName,
        phone = phone,
        region = region
    )

}

@Serializable
data class Barber(
    var id: Int,
    val store_id: Int,
    val first_name: String,
    val last_name: String,
    val phone: String,
    val region: Int,
)