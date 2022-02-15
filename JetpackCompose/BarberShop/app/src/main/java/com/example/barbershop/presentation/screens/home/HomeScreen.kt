package com.example.barbershop.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barbershop.presentation.component.ImageSlider
import com.example.barbershop.presentation.screens.destinations.DetailsScreenDestination
import com.example.barbershop.presentation.screens.destinations.SearchScreenDestination
import com.example.barbershop.ui.theme.backgroundColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 56.dp),
        topBar = {
            HomeTopAppBar(
                onSearchClicked = {
                    navigator.navigate(
                        SearchScreenDestination()
                    )
                }
            )
        },
        content = {
            HomeScreenContent(
                onItemClicked = {
                    navigator.navigate(
                        DetailsScreenDestination()
                    )
                },
                addToFavoriteClicked = {}
            )
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HomeScreenContent(
    onItemClicked: () -> Unit,
    addToFavoriteClicked: () -> Unit,
) {
    val lazyState = rememberLazyListState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.backgroundColor
    ) {
        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                ImageSlider()
            }
            items(count = 4) {
                BarberShopItem(
                    onItemClicked = onItemClicked,
                    addToFavoriteClicked = addToFavoriteClicked,
                    moreItemClicked = {}
                )
            }
        }
    }

}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        onItemClicked = {},
        addToFavoriteClicked = {}
    )
}