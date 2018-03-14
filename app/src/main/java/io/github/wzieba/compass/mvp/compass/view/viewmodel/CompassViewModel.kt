package io.github.wzieba.compass.mvp.compass.view.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import io.github.wzieba.compass.BR

class CompassViewModel : BaseObservable() {

    @get:Bindable
    var arrowRotation: Float = 0f
        set(arrowRotation) {
            field = arrowRotation
            notifyPropertyChanged(BR.arrowRotation)
        }
}