package io.github.wzieba.compass.domain.location

import android.location.Address
import android.location.Geocoder
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class ProvideLocationNameUseCase @Inject constructor(private val geocoder: Geocoder) {

    fun buildUseCaseObservable(latLng: LatLng?): Observable<BasicLocationData> {
        return Observable.just(latLng ?: INVALID_LAT_LNG)
                .map { ll ->
                    geocoder.getFromLocation(ll.lat, ll.lng, 1)
                }
                .map { list ->
                    list.firstOrNull()
                }
                .map { address: Address? ->
                    BasicLocationData(
                            address?.countryName ?: NO_LOCATION_DATA.countryName,
                            address?.locality ?: NO_LOCATION_DATA.cityName
                    )
                }
                .onErrorReturn {
                    it.printStackTrace()
                    INVALID_INPUT_DATA
                }
                .subscribeOn(Schedulers.io())
    }
}