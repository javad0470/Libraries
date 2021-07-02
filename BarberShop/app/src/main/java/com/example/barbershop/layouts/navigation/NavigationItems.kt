package com.example.barbershop.layouts.navigation

import com.example.barbershop.R

sealed class NavigationItems(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItems("home", R.drawable.ic_home, "خانه")
    object Favorites : NavigationItems("favorites", R.drawable.ic_heart, "مورد علاقه")
    object Wallet : NavigationItems("wallet", R.drawable.ic_wallet, "کیف پول")
    object Notifications : NavigationItems("notifications", R.drawable.ic_message, "پیام ها")
    object Profile : NavigationItems("profile", R.drawable.ic_profile, "پروفایل")
}