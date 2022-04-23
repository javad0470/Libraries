package com.example.barbershop.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.barbershop.data.response.home.*
import com.example.barbershop.ui.theme.iranSans
import com.example.barbershop.utils.Constants.BANNER
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun ImageSlider(homeResult: List<HomeResult>?) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var homeSubItems: List<HomeSubItem>? = emptyList()
    val pagerState = rememberPagerState()

    homeResult?.forEach { response ->
        if (response.item == BANNER) {
            homeSubItems = response.subItems
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (homeSubItems != null) {
            while (true) {
                yield()
                delay(5000)
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % pagerState.pageCount)
            }
        }
    }

    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        count = if (homeSubItems.isNullOrEmpty()) 0 else homeSubItems!!.size,
        state = pagerState,
    ) { position ->
        Card(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(screenWidth / 2f)
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(position).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                },
            shape = RoundedCornerShape(20.dp)
        ) {
            ImageSliderContent(
                homeSubItem = homeSubItems!![position],
            )
        }
    }

}

@ExperimentalPagerApi
@Composable
fun ImageSliderContent(
    homeSubItem: HomeSubItem,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context)
                .data(data = homeSubItem.image?.mq)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Image Slider",
            contentScale = ContentScale.Crop,
            error = { NoImage() },
            loading = { LoadingImage() }
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            color = Color.Black.copy(0.5f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, end = 32.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = homeSubItem.title,
                    fontFamily = iranSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

    }

}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun ImageSliderPreview() {
    ImageSliderContent(
        homeSubItem = HomeSubItem(
            address = "",
            cityId = 0,
            detail = "",
            id = "",
            image = HomeImage(
                createdAt = "",
                hq = "https://img.bfmtv.com/c/630/420/871/7b9f41477da5f240b24bd67216dd7.jpg",
                id = "",
                lq = "",
                mq = ""
            ),
            locationImages = HomeLocationImage(
                createdAt = "",
                hq = "https://www.akamai.com/site/im-demo/perceptual-standard.jpg",
                id = "",
                lq = "",
                mq = ""
            ),
            profilePicture = HomeProfilePicture(
                createdAt = "",
                hq = "",
                id = "",
                lq = "",
                mq = ""
            ),
            provinceId = 0,
            rate = 0f,
            title = "جنگ جنگ تا پیروزی",
            bookmarked = false
        ),
    )
}

