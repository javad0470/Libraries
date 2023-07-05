package com.example.sessions

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "phone",
    ){
        composable(route = "phone"){

        }
        composable(route = "verify"){}
        composable(route = ""){

        }


    }

}