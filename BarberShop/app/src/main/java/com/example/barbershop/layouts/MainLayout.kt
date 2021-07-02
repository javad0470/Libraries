package com.example.barbershop.layouts

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.barbershop.layouts.navigation.*


@Composable
fun MainLayout() {
    val navController = rememberNavController()
    Scaffold(topBar = { ToolBarMain() },bottomBar = { BottomNavigation(navController)}) {
        Navigation(navController = navController)
    }
}

@Composable
fun ToolBarMain() {
    val context = LocalContext.current.applicationContext
    TopAppBar(title = {
        Text(
            text = "BarberShop",
            color = Color(0xFF272727),
        )
    },
        elevation = 12.dp,
        backgroundColor = Color(0xFFF9F9F9),
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF272727)
                )
            }
            IconButton(onClick = { Toast.makeText(context, "search", Toast.LENGTH_SHORT).show() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF272727)
                )
            }
        }
    )
}

@Composable
fun BottomNavigation(navController: NavController){
    val items = listOf(
        NavigationItems.Home,
        NavigationItems.Favorites,
        NavigationItems.Wallet,
        NavigationItems.Notifications,
        NavigationItems.Profile,
    )
    BottomNavigation(backgroundColor = Color(0xFFF9F9F9),elevation = 12.dp) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(icon = {Icon(painterResource(id = item.icon), contentDescription = item.title)},
                label = { Text(text = item.title) },selectedContentColor = Color(0xFF272727),
                unselectedContentColor = Color(0xFF272727).copy(alpha = 0.4f),
                alwaysShowLabel = true,selected = currentRoute == item.route
                ,onClick = {
                    /*** Bottom Bar Items Click  ***/
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route){
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

    }
}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = NavigationItems.Home.route ){
        composable(NavigationItems.Home.route){ HomeScreen() }
        composable(NavigationItems.Favorites.route){ FavoriteScreen() }
        composable(NavigationItems.Wallet.route){ WalletScreen() }
        composable(NavigationItems.Notifications.route){ NotificationScreen() }
        composable(NavigationItems.Profile.route){ ProfileScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun MainLayoutPreview(){
    MainLayout()
}

