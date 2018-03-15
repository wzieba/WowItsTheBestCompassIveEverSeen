package io.github.wzieba.compass.domain.location

import android.location.Location
import io.github.wzieba.compass.model.LatLng
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val INVALID_DISTANCE = -1f

class ComputeDistanceUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    fun buildUseCaseObservable(distanceTo: LatLng?): Observable<Float> {
        return Observable.just(distanceBetween(locationRepository.getCurrentLocation(), distanceTo)).subscribeOn(Schedulers.io())
    }

    private fun distanceBetween(from: LatLng, to: LatLng?): Float {
        if (to != null) {
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
        return INVALID_DISTANCE
    }
}