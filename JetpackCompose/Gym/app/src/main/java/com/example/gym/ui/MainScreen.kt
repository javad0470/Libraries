package com.example.gym.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Devices.AUTOMOTIVE_1024p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable()
fun MainScreen() {
    val configuration = LocalConfiguration.current
    when(configuration.orientation){
        Configuration.ORIENTATION_PORTRAIT -> {
            PortraitMainScreen()
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeMainScreen()
        }
        else -> Unit
    }
}

@Composable
fun PortraitMainScreen() {

}

@Composable
fun LandscapeMainScreen() {

}

@Preview(
    device = AUTOMOTIVE_1024p,
    showBackground = true
)
@Composable
fun MainScreenPreview() {
    MainScreen()
}