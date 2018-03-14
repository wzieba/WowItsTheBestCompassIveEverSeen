package io.github.wzieba.compass.data

import android.location.Address
import android.location.Geocoder
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.BasicLocationData
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.model.NO_LOCATION_DATA
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class ProvideLocationNameUseCase @Inject constructor(private val geocoder: Geocoder) {

    fun buildUseCaseObservable(latLng: LatLng): Observable<BasicLocationData> {
        return Observable.just(geocoder.getFromLocation(latLng.lat, latLng.lng, 1)
                .map { address: Address ->
                    BasicLocationData(
                            address.countryName ?: NO_LOCATION_DATA.countryName,
                            address.locality ?: NO_LOCATION_DATA.cityName
                    )
                }
                .firstOrNull()
                ?: NO_LOCATION_DATA
        ).observeOn(Schedulers.io())
    }

}