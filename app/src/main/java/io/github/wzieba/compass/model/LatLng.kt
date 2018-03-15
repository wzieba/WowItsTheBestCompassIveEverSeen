package io.github.wzieba.compass.model

val INVALID_LAT_LNG = LatLng(10000.0, 10000.0)

data class LatLng(val lat: Double, val lng: Double)