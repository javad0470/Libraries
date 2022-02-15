package com.example.barbershop.presentation.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.barbershop.presentation.screens.details.booking.BookingScreen
import com.example.barbershop.presentation.screens.details.comment.CommentScreen
import com.example.barbershop.presentation.screens.details.specification.SpecificationScreen
import com.example.barbershop.utils.TabsItem
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Destination
@Composable
fun DetailsScreen(
    navigator: DestinationsNavigator,
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DetailsTopAppBar(
                onBackClicked = {
                    navigator.popBackStack()
                }
            )
        },
        content = {
            DetailsScreenContent(
                pagerState = rememberPagerState(),
                navigator = navigator
            )
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun DetailsScreenContent(
    navigator: DestinationsNavigator,
    pagerState: PagerState,
) {
    val tabs = listOf(
        TabsItem.Booking,
        TabsItem.Specification,
        TabsItem.Comment,
    )

    Column(modifier = Modifier.fillMaxSize()) {

        DetailsTabs(tabs = tabs, pagerState = pagerState)

        HorizontalPager(
            state = pagerState,
            count = tabs.size
        ) { page ->
            when (tabs[page]) {
                is TabsItem.Booking -> {
                    BookingScreen(
                        navigator = navigator
                    )
                }
                is TabsItem.Specification -> {
                    SpecificationScreen()
                }
                is TabsItem.Comment -> {
                    CommentScreen()
                }
            }
        }
    }
}

