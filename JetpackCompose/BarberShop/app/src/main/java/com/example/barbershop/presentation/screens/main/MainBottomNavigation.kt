package com.example.barbershop.presentation.screens.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershop.ui.theme.*
import com.example.barbershop.utils.BottomNavigationItems

@Composable
fun MainBottomNavigation(
    screenState: String,
    screen: (screen: String) -> Unit,
) {
    val items = listOf(
        BottomNavigationItems.Home,
        BottomNavigationItems.Favorite,
        BottomNavigationItems.BookingPlan,
        BottomNavigationItems.Account,
    )
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.bottomNavigationBarColor,
        elevation = 16.dp
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = screenState == item.route,
                onClick = { screen.invoke(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontFamily = iranSans,
                        fontWeight = if (screenState == item.route)
                            FontWeight.Medium
                        else
                            FontWeight.Normal,
                        fontSize = bottomNavigationItemsFontSize
                    )
                },
                alwaysShowLabel = true,
                selectedContentColor = if (isSystemInDarkTheme())
                    primaryGold.copy(0.9f)
                else
                    primaryBlue.copy(0.9f),
                unselectedContentColor = if (isSystemInDarkTheme())
                    Color.White.copy(alpha = ContentAlpha.disabled)
                else
                    Color.Black.copy(alpha = ContentAlpha.disabled)
            )
        }

    }
}