package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    var id: Int,
    var name: String,
    var image: String,
    var about: String,
    var rating: Double,
    var power: Int,
    var month: String,
    var day: String,
    var family: List<String>,
    var abilities: List<String>,
    var natureTypes: List<String>
)
