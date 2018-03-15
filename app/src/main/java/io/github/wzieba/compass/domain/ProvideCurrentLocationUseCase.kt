package io.github.wzieba.compass.domain

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.domain.location.LocationRepository
import io.github.wzieba.compass.model.LatLng
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@ActivityScope
class ProvideCurrentLocationUseCase @Inject constructor(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val notifier: PublishSubject<LatLng>,
        private val locationRepository: LocationRepository
) : LocationCallback() {

    fun registerRequestLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(LocationRequest.create(), this, null)
    }

    fun unregisterRequestLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(this)
    }

    override fun onLocationResult(locationResult: LocationResult?) {
        super.onLocationResult(locationResult)
        with(locationResult) {
            if (this != null) {
                val currentLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
                locationRepository.saveCurrentLocation(currentLocation)
                notifier.onNext(currentLocation)
            }
        }
    }

    fun asObservable(): Observable<LatLng> {
        return notifier.observeOn(Schedulers.io())
    }
}