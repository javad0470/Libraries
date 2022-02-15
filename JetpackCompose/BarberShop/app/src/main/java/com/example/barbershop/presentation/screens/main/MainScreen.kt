package com.example.barbershop.presentation.screens.main

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.presentation.screens.account.AccountScreen
import com.example.barbershop.presentation.screens.destinations.NoConnectionScreenDestination
import com.example.barbershop.presentation.screens.notification.NotificationScreen
import com.example.barbershop.presentation.screens.favorite.FavoriteScreen
import com.example.barbershop.presentation.screens.home.HomeScreen
import com.example.barbershop.utils.BottomNavigationItems
import com.example.barbershop.utils.ConnectionState
import com.example.barbershop.viewmodels.SharedViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    sharedViewModel: SharedViewModel = hiltViewModel(),
) {
    Log.d("Kotlin", sharedViewModel.connectionState.value.toString())
    when (sharedViewModel.connectionState.value) {
        true -> {
           // navigator.popBackStack()
        }
        false -> {
          //  navigator.navigate(NoConnectionScreenDestination)
        }

    }

    MainScreenContent(
        navigator = navigator,
        sharedViewModel = sharedViewModel
    )
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainScreenContent(
    navigator: DestinationsNavigator,
    sharedViewModel: SharedViewModel,
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize(),
        bottomBar = {
            MainBottomNavigation(
                screenState = sharedViewModel.screenState,
                screen = { screen ->
                    sharedViewModel.screenState = screen
                }
            )
        },
        content = {
            Crossfade(targetState = sharedViewModel.screenState) { screen ->
                when (screen) {
                    BottomNavigationItems.Home.route -> {
                        HomeScreen(
                            navigator = navigator
                        )
                    }
                    BottomNavigationItems.Favorite.route -> {
                        FavoriteScreen()
                    }
                    BottomNavigationItems.BookingPlan.route -> {
                        NotificationScreen()
                    }
                    BottomNavigationItems.Account.route -> {
                        AccountScreen(
                            navigator = navigator
                        )
                    }
                }
            }
        }
    )
}