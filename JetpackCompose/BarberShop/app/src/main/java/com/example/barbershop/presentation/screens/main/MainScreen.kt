package com.example.barbershop.presentation.screens.main

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.presentation.screens.account.AccountScreen
import com.example.barbershop.presentation.screens.notification.NotificationScreen
import com.example.barbershop.presentation.screens.favorite.FavoriteScreen
import com.example.barbershop.presentation.screens.home.HomeScreen
import com.example.barbershop.utils.BottomNavigationItems
import com.example.barbershop.viewmodels.SharedViewModel
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.barbershop.presentation.screens.account.AccountViewModel
import com.example.barbershop.presentation.screens.registration.RegistrationViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Destination()
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    accountViewModel: AccountViewModel,
    sharedViewModel: SharedViewModel = hiltViewModel(),
) {
    MainScreenContent(
        navigator = navigator,
        sharedViewModel = sharedViewModel,
        accountViewModel = accountViewModel
    )
}

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainScreenContent(
    navigator: DestinationsNavigator,
    sharedViewModel: SharedViewModel,
    accountViewModel: AccountViewModel,
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
                        FavoriteScreen(navigator = navigator)
                    }
                    BottomNavigationItems.BookingPlan.route -> {
                        NotificationScreen(navigator = navigator)
                    }
                    BottomNavigationItems.Account.route -> {
                        AccountScreen(
                            navigator = navigator,
                            accountViewModel = accountViewModel
                        )
                    }
                }
            }
        }
    )
}