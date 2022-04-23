package com.example.barbershop.presentation.screens.splash

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.presentation.screens.NavGraphs
import com.example.barbershop.presentation.screens.destinations.*
import com.example.barbershop.presentation.screens.registration.RegistrationViewModel
import com.example.barbershop.ui.theme.textColor
import com.example.barbershop.utils.Constants
import com.example.barbershop.utils.Constants.SPLASH_SCREEN
import com.example.barbershop.viewmodels.SharedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Destination(
    start = true,
    route = SPLASH_SCREEN,
)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    splashViewModel: SplashViewModel = hiltViewModel(),
    registrationViewModel: RegistrationViewModel,
) {
    val onBoardingCompleted by splashViewModel.onBoardingCompleted.collectAsState()
    val token = registrationViewModel.token.collectAsState().value

    LaunchedEffect(key1 = onBoardingCompleted, key2 = token) {

        if (onBoardingCompleted) {
//            navigator.navigate(RegisterScreenDestination)
//            { popUpTo(SPLASH_SCREEN) { inclusive = true } }
            if (token.isNotEmpty()) {
                navigator.navigate(MainScreenDestination()) {
                    popUpTo(SPLASH_SCREEN) { inclusive = true }
                }
            } else {
                navigator.navigate(VerificationPhoneNumberScreenDestination) {
                    popUpTo(SPLASH_SCREEN) { inclusive = true }
                }
            }
        } else {
            navigator.navigate(WelcomeScreenDestination) {
                popUpTo(SPLASH_SCREEN) { inclusive = true }
            }
        }

        delay(3000)
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