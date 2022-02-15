package com.example.barbershop.presentation.screens.favorite

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun FavoriteScreen() {
    Scaffold(
        topBar = { FavoriteTopAppBar() },
        content = {}
    )
}