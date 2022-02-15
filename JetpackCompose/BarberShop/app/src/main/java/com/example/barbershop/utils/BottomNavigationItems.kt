package com.example.barbershop.utils

import com.example.barbershop.R

sealed class BottomNavigationItems(
    val id: Int,
    val route: String,
    val icon: Int,
    val label: String,
) {

    object Home : BottomNavigationItems(
        id = 0,
        route = "home_screen",
        icon = R.drawable.ic_home,
        label = "خانه"
    )

    object Favorite : BottomNavigationItems(
        id = 1,
        route = "favorite_screen",
        icon = R.drawable.ic_favorite,
        label = "موردعلاقه ها"
    )

    object BookingPlan : BottomNavigationItems(
        id = 2,
        route = "notification_screen",
        icon = R.drawable.ic_notification,
        label = "اعلانات"
    )

    object Account : BottomNavigationItems(
        id = 3,
        route = "account_screen",
        icon = R.drawable.ic_user,
        label = "حساب کاربری"
    )

}
