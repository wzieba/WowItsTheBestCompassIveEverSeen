package io.github.wzieba.compass.model

val NO_LOCATION_DATA = BasicLocationData("Unknown country", "Unknown city")

data class BasicLocationData(val countryName: String, val cityName: String)