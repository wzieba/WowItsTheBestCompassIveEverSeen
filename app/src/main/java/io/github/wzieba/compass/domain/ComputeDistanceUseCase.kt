package io.github.wzieba.compass.domain

import android.location.Location
import io.github.wzieba.compass.domain.location.LocationRepository
import io.github.wzieba.compass.model.LatLng
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ComputeDistanceUseCase @Inject constructor(val locationRepository: LocationRepository) {

    fun buildUseCaseObservable(distanceTo: LatLng): Observable<Float> {
        return Observable.just(distanceBetween(locationRepository.getCurrentLocation(), distanceTo)).observeOn(Schedulers.io())
    }

    private fun distanceBetween(from: LatLng, to: LatLng): Float {
        val results = FloatArray(3)
        Location.distanceBetween(
                from.lat,
                from.lng,
                to.lat,
                to.lng,
                results
        )
        return results[0]
    }
}