package com.example.barbershop.presentation.screens.favorite

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.data.response.home.HomeSubItem
import com.example.barbershop.presentation.component.LocalErrorScreen
import com.example.barbershop.presentation.component.LoadingScreen
import com.example.barbershop.presentation.screens.destinations.DetailsScreenDestination
import com.example.barbershop.ui.theme.backgroundColor
import com.example.barbershop.utils.RequestState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun FavoriteScreen(
    navigator: DestinationsNavigator,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {
    val allFavoriteItems = favoriteViewModel.allFavoriteItemsResponse.collectAsState().value
    var isRefreshing by remember { mutableStateOf(false) }


    when (allFavoriteItems) {
        is RequestState.Idle -> {
            LaunchedEffect(key1 = allFavoriteItems) {
                launch(Dispatchers.IO) {
                    favoriteViewModel.getAllFavoriteItems()
                }
            }
        }
        is RequestState.Loading -> {
            LoadingScreen()
        }
        is RequestState.Success -> {
            FavoriteScreenContent(
                homeSubItems = allFavoriteItems.data,
                onItemClicked = { item ->
                    navigator.navigate(DetailsScreenDestination(
                        barberShopItem = item,
                        title = item.title)
                    )
                },
            )
        }
        is RequestState.Error -> {
            LocalErrorScreen(
                onRefresh = {
                    isRefreshing = true
                    favoriteViewModel.getAllFavoriteItems()
                    isRefreshing = false
                },
                isRefreshing = isRefreshing
            )
        }
    }

}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun FavoriteScreenContent(
    homeSubItems: List<HomeSubItem>,
    onItemClicked: (HomeSubItem) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .padding(bottom = 56.dp)
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.backgroundColor,
        topBar = { FavoriteTopAppBar() },
        content = {
            if (homeSubItems.isNullOrEmpty()) {
                FavoriteNoContentScreen()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        bottom = 16.dp,
                        top = 16.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = homeSubItems,
                        key = { it.id },
                    ) { homeSubItem ->
                        FavoriteItem(
                            modifier = Modifier.animateItemPlacement(
                                animationSpec = tween(600),
                            ),
                            onItemClicked = { onItemClicked(homeSubItem) },
                            homeSubItem = homeSubItem
                        )
                    }
                }
            }
        }
    )
}