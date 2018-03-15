package io.github.wzieba.compass.domain.location

import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.LatLng
import javax.inject.Inject

//Mock
private var CURRENT_LOCATION: LatLng = LatLng(0.0, 0.0)

@ActivityScope
class LocationRepositoryImpl @Inject constructor() : LocationRepository {

    override fun getCurrentLocation(): LatLng {
        return CURRENT_LOCATION
    }

    override fun saveCurrentLocation(latLng: LatLng) {
        CURRENT_LOCATION = latLng
    }
}