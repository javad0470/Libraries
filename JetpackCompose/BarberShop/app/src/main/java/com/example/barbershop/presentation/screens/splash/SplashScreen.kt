package com.example.barbershop.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.presentation.screens.destinations.*
import com.example.barbershop.ui.theme.textColor
import com.example.barbershop.viewmodels.SharedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Destination(
    start = true,
    route = "splash_screen"
)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    LaunchedEffect(key1 = true) {
        delay(1000)

        navigator.navigate(
            MainScreenDestination()
        ) {
            this.popUpTo("splash_screen") { inclusive = true }
        }
    }

    SplashContent()
}

@Composable
fun SplashContent() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Splash", color = MaterialTheme.colors.textColor.copy(0.5f))
    }

}