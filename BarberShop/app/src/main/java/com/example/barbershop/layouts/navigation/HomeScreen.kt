package com.example.barbershop.layouts.navigation

import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershop.viewmodel.MainViewModel

@Composable
fun HomeScreen(){
    Text(text = "HomeScreen")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}

fun getSlider(){

}