package com.example.barbershop.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershop.R
import com.example.barbershop.ui.theme.activePagerIndicatorColor
import com.example.barbershop.ui.theme.inactivePagerIndicatorColor
import com.example.barbershop.ui.theme.iranSans
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun ImageSlider() {
    ImageSliderContent()
}

@ExperimentalPagerApi
@Composable
fun ImageSliderContent() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth()
            .height(screenWidth / 2f)
            .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Image Slider",
            contentScale = ContentScale.Crop
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "سالن مکران",
                    fontFamily = iranSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.White
                )

                HorizontalPagerIndicator(
                    pagerState = rememberPagerState(
                        initialPage = 5
                    ),
                    activeColor = MaterialTheme.colors.activePagerIndicatorColor,
                    inactiveColor = MaterialTheme.colors.inactivePagerIndicatorColor,
                    indicatorWidth = 10.dp,
                    indicatorHeight = 10.dp,
                    spacing = 2.dp
                )
            }
        }

    }

}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun ImageSliderPreview() {
    ImageSliderContent()
}