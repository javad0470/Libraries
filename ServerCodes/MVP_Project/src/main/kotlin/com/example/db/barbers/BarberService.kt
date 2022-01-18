package com.example.db.barbers

import org.jetbrains.exposed.sql.transactions.transaction

class BarberService {

    fun getAllBarbers(): Iterable<Barber> = transaction {
        BarberEntity.all().map(BarberEntity::toBarber)
    }

    fun addBarber(barber: Barber) = transaction {
        BarberEntity.new {
            this.storeId = barber.store_id
            this.firstName = barber.first_name
            this.lastName = barber.last_name
            this.phone = barber.phone
            this.region = barber.region
        }
    }

    fun updateBarber(barber: Barber) = transaction {
        val item = BarberEntity.findById(id = barber.id)
        item!!.storeId = barber.store_id
        item.firstName = barber.first_name
        item.lastName = barber.last_name
        item.phone = barber.phone
        item.region = barber.region
    }

    fun showSpecificItem(barberId: Int) : Iterable<Barber> = transaction {
        BarberEntity.find { BarberTable.id eq barberId }.map(BarberEntity::toBarber)
    }

}