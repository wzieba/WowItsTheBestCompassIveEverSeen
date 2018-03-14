package io.github.wzieba.compass.mvp.compass.view

import android.view.View
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel

interface CompassView {
    fun asView(): View
    fun setViewModel(viewModel: CompassViewModel)
    fun showDestinationLocationInput(latLng: LatLng)
    fun hideDestinationLocationInput()
    fun setListener(listener: Listener)

    interface Listener {
        fun onShowCoordinatesInputClick()
        fun onHideCoordinatesInputClick()
    }

}