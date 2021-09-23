package com.example.osmproject

import org.osmdroid.util.GeoPoint

data class Items(
    var id:Int,
    var geoPoint: GeoPoint,
    var title:String
)
