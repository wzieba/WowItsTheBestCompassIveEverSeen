package io.github.wzieba.compass.mvp.compass.view.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import io.github.wzieba.compass.BR

class CompassViewModel : BaseObservable() {


    @get:Bindable
    var latCoordinate: Double = 18.454660
        set(latCoordinate) {
            field = latCoordinate
            notifyPropertyChanged(BR.arrowRotation)
        }

    @get:Bindable
    var lngCoordinate: Double = -66.092913
        set(arrowRotation) {
            field = arrowRotation
            notifyPropertyChanged(BR.arrowRotation)
        }

    @get:Bindable
    var arrowRotation: Float = 0f
        set(arrowRotation) {
            field = arrowRotation
            notifyPropertyChanged(BR.arrowRotation)
        }

    @get:Bindable
    var isLocationInputVisible: Boolean = false
        set(isLocationInputVisible) {
            field = isLocationInputVisible
            notifyPropertyChanged(BR.locationInputVisible)
        }
}