package com.example.db.items

import org.jetbrains.exposed.sql.transactions.transaction

class BarberShopService {

    fun getAllBarberShops(): Iterable<BarberShop> = transaction {
        BarberShopEntity.all().map(BarberShopEntity::toBarberShop)
    }

    fun addBarberShop(item: BarberShop) = transaction {
        BarberShopEntity.new {
            this.title = item.title
            this.address = item.address
            this.services = item.services
            this.description = item.description
            this.details = item.details
            this.datePlan = item.date_plan
            this.image = item.image
            this.rate = item.rate
            this.province = item.province
            this.city = item.city
        }
    }

    fun deleteBarberShop(itemId: Int) = transaction {
        BarberShopEntity[itemId].delete()
    }

}