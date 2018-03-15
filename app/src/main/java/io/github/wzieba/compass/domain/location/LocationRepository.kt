package io.github.wzieba.compass.domain.location

import io.github.wzieba.compass.model.LatLng

interface LocationRepository {

    fun getCurrentLocation(): LatLng

    fun saveCurrentLocation(latLng: LatLng)

}