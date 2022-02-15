package com.example.barbershop.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershop.R
import com.example.barbershop.ui.theme.iranSans
import com.example.barbershop.ui.theme.topAppbarColor
import com.example.barbershop.ui.theme.topAppbarContentColor

@Composable
fun HomeTopAppBar(
    onSearchClicked: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.topAppbarColor,
        title = {
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "سلمونی",
                fontFamily = iranSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colors.topAppbarContentColor
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked.invoke() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colors.topAppbarContentColor
                )
            }
        }
    )
}