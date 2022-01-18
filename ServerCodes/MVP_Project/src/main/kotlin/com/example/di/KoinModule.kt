package com.example.di

import com.example.db.barbers.BarberService
import com.example.db.items.BarberShopService
import org.koin.dsl.module

val koinModule = module {
    single {
        BarberShopService()
    }
    single {
        BarberService()
    }
}