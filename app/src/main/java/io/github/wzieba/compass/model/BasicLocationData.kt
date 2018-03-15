package io.github.wzieba.compass.model

val NO_LOCATION_DATA = BasicLocationData("Unknown country", "Unknown city")
val INVALID_INPUT_DATA = BasicLocationData("Provide correct data", "Error")

data class BasicLocationData(val countryName: String, val cityName: String)