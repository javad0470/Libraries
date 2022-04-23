package com.example.barbershop.presentation.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbershop.presentation.screens.details.booking.BookingScreen
import com.example.barbershop.presentation.screens.details.comment.CommentScreen
import com.example.barbershop.presentation.screens.details.specification.SpecificationScreen
import com.example.barbershop.utils.TabsItem
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.*
import com.example.barbershop.data.response.home.HomeSubItem
import com.example.barbershop.presentation.component.CustomSnackBar
import com.example.barbershop.utils.MessageBarState
import com.example.barbershop.utils.MessageBarType
import com.google.accompanist.pager.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Destination
@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    navigator: DestinationsNavigator,
    barberShopItem: HomeSubItem,
    title: String,
) {
    val isBookmarked = detailsViewModel.isBookmarked.collectAsState().value
//    var messageBarState by remember {
//        mutableStateOf(
//            MessageBarState(
//                message = "",
//                messageBarType = MessageBarType.Nothing
//            )
//        )
//    }
    val scope = rememberCoroutineScope()

    DisposableEffect(key1 = Unit) {
        onDispose {
            detailsViewModel.cleanAllResponse()
        }
    }

    BackHandler {
        navigator.popBackStack()
    }

    LaunchedEffect(key1 = barberShopItem, key2 = isBookmarked) {
        detailsViewModel.isBookmarked(id = barberShopItem.id)
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DetailsTopAppBar(
                title = title,
                onBackClicked = {
                    navigator.popBackStack()
                },
                bookmarkClicked = {
                    scope.launch {
                        detailsViewModel.addToFavorite(item = barberShopItem)
                        detailsViewModel.messageBarState.update { currentMessageBar ->
                            currentMessageBar.copy(
                                message = "با موفقیت به نشان شده ها افزوده شد",
                                messageBarType = MessageBarType.Info,
                                actionText = "باشه"
                            )
                        }
                    }
                },
                unBookmarkClicked = {
                    scope.launch {
                        detailsViewModel.deleteFromFavorite(id = barberShopItem.id)
                        detailsViewModel.messageBarState.update { currentMessageBar ->
                            currentMessageBar.copy(
                                message = "با موفقیت از نشان شده ها حذف شد",
                                messageBarType = MessageBarType.Info,
                                actionText = "باشه"
                            )
                        }
                    }
                },
                isBookmarked = isBookmarked ?: false
            )
        },
        content = {
            DetailsScreenContent(
                detailsViewModel = detailsViewModel,
                pagerState = rememberPagerState(),
                navigator = navigator,
                barberShopID = barberShopItem.id,
            )
        }
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun DetailsScreenContent(
    detailsViewModel: DetailsViewModel,
    navigator: DestinationsNavigator,
    pagerState: PagerState,
    barberShopID: String,
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
                        detailsViewModel = detailsViewModel,
                        navigator = navigator,
                        barberShopID = barberShopID
                    )
                }
                is TabsItem.Specification -> {
                    SpecificationScreen(
                        detailsViewModel = detailsViewModel,
                        barberShopID = barberShopID
                    )
                }
                is TabsItem.Comment -> {
                    CommentScreen(
                        detailsViewModel = detailsViewModel,
                        barbershopID = barberShopID
                    )
                }
            }
        }
    }

    CustomSnackBar(
        messageBarState = detailsViewModel.messageBarState,
    )

}

