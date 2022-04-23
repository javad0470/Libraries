package com.example.barbershop.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.data.response.home.HomeResult
import com.example.barbershop.data.response.home.HomeSubItem
import com.example.barbershop.presentation.component.LocalErrorScreen
import com.example.barbershop.presentation.component.ImageSlider
import com.example.barbershop.presentation.component.LoadingScreen
import com.example.barbershop.presentation.component.RemoteErrorScreen
import com.example.barbershop.presentation.screens.destinations.DetailsScreenDestination
import com.example.barbershop.presentation.screens.destinations.MoreHomeScreenDestination
import com.example.barbershop.presentation.screens.destinations.SearchScreenDestination
import com.example.barbershop.presentation.screens.favorite.FavoriteViewModel
import com.example.barbershop.ui.theme.backgroundColor
import com.example.barbershop.utils.Constants.LANDSCAPE
import com.example.barbershop.utils.Constants.SUCCESSFULLY
import com.example.barbershop.utils.Constants.UNEXPECTED_ERROR
import com.example.barbershop.utils.RequestState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val homeResponse = homeViewModel.homeResponse.collectAsState().value
    var isRefreshing by remember { mutableStateOf(false) }

    when (homeResponse) {
        is RequestState.Idle -> {
            LaunchedEffect(key1 = homeResponse) {
                homeViewModel.getHomeScreen()
            }
        }
        is RequestState.Loading -> {
            LoadingScreen()
        }
        is RequestState.Success -> {
            when (homeResponse.data.contentType) {
                SUCCESSFULLY -> {
                    HomeScreenUI(
                        navigator = navigator,
                        homeResult = homeResponse.data.result,
                        onRefreshing = {
                            isRefreshing = true
                            homeViewModel.getHomeScreen()
                            isRefreshing = false
                        },
                        refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    )
                }
                UNEXPECTED_ERROR -> {
                    RemoteErrorScreen(
                        onRefresh = {
                            isRefreshing = true
                            homeViewModel.getHomeScreen()
                            isRefreshing = false
                        },
                        isRefreshing = isRefreshing
                    )
                }
                "no_data" -> {
                    HomeEmptyContent(homeViewModel = homeViewModel)
                }
                "TOKEN_INVALID" -> {
                    Log.d("Kotlin", "TOKEN_INVALID")
                }
            }
        }
        is RequestState.Error -> {
            RemoteErrorScreen(
                onRefresh = {
                    isRefreshing = true
                    homeViewModel.getHomeScreen()
                    isRefreshing = false
                },
                isRefreshing = isRefreshing
            )
        }
    }

}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun HomeScreenUI(
    navigator: DestinationsNavigator,
    homeResult: List<HomeResult>?,
    onRefreshing: () -> Unit,
    refreshState: SwipeRefreshState,
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 56.dp),
        topBar = {
            HomeTopAppBar(
                onSearchClicked = { navigator.navigate(SearchScreenDestination) }
            )
        },
        content = {
            SwipeRefresh(
                state = refreshState,
                onRefresh = onRefreshing
            ) {
                HomeScreenContent(
                    onItemClicked = { item ->
                        navigator.navigate(DetailsScreenDestination(
                            barberShopItem = item,
                            title = item.title
                        ))
                    },
                    moreItemClicked = { moreAction, title ->
                        navigator.navigate(
                            MoreHomeScreenDestination(
                                moreAction = moreAction,
                                title = title
                            )
                        )
                    },
                    homeResult = homeResult,
                )
            }
        }
    )

}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HomeScreenContent(
    onItemClicked: (HomeSubItem) -> Unit,
    moreItemClicked: (moreAction: String, title: String) -> Unit,
    homeResult: List<HomeResult>?,
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
                ImageSlider(
                    homeResult = homeResult
                )
            }
            items(items = homeResult ?: emptyList()) { homeResult ->
                if (homeResult.item == LANDSCAPE) {
                    BarberShopItem(
                        onItemClicked = onItemClicked,
                        moreItemClicked = moreItemClicked,
                        homeResult = homeResult,
                    )
                }
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
        moreItemClicked = { m, t -> },
        homeResult = emptyList(),
    )
}