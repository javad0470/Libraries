package com.example.worldskills.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.worldskills.ui.screens.login.PhoneScreen
import com.example.worldskills.utils.Constant.PHONE_SCREEN
import com.example.worldskills.viewModels.SharedViewModel
import com.google.accompanist.navigation.animation.composable


@ExperimentalAnimationApi
fun NavGraphBuilder.phoneComposable(
    navigateToSmsScreen: (phone: String) -> Unit,
    sharedViewModel: SharedViewModel
) {

    composable(route = PHONE_SCREEN) {
        PhoneScreen(
            navigateToSmsScreen = navigateToSmsScreen,
            sharedViewModel = sharedViewModel
        )
    }
}