package io.github.wzieba.compass.mvp.compass.presenter

import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.domain.compass.CompassValueChangeEmitter
import io.github.wzieba.compass.domain.location.ComputeDistanceUseCase
import io.github.wzieba.compass.domain.location.CurrentLocationEmitter
import io.github.wzieba.compass.domain.location.ProvideLocationNameUseCase
import io.github.wzieba.compass.formatToDistanceReadable
import io.github.wzieba.compass.model.BasicLocationData
import io.github.wzieba.compass.model.CompassIndication
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.mvp.compass.VIEW_MODEL
import io.github.wzieba.compass.mvp.compass.view.CompassView
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.Serializable
import java.util.*
import javax.inject.Inject

//TODO add use case for providing heading to location, not north as now

@ActivityScope
class CompassPresenter @Inject constructor(
        private val compassView: CompassView,
        private val provideLocationNameUseCase: ProvideLocationNameUseCase,
        private val compassValueChangeEmitter: CompassValueChangeEmitter,
        private val currentLocationEmitter: CurrentLocationEmitter,
        private val computeDistanceUseCase: ComputeDistanceUseCase
) : CompassView.Listener {

    private var viewModel: CompassViewModel? = null

    fun onCreate(state: Map<String, Serializable>?) {
        if (state != null) {
            viewModel = state[VIEW_MODEL] as CompassViewModel?
        }

        if (viewModel == null)
            viewModel = CompassViewModel()

        viewModel?.let {
            compassView.setViewModel(it)
        }

        compassView.setListener(this)

        compassValueChangeEmitter.asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { compassIndication: CompassIndication ->
                    viewModel?.arrowRotation = compassIndication.degree.toFloat()
                }
                .subscribe()

        currentLocationEmitter.asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { _: LatLng ->
                    updateDistance()
                }
                .subscribe()
    }

    fun onResume() {
        compassValueChangeEmitter.registerListener()
        currentLocationEmitter.registerRequestLocationUpdates()
    }

    fun getStateForSave(): Map<String, Serializable> {
        val state = HashMap<String, Serializable>()
        if (viewModel != null) {
            state[VIEW_MODEL] = viewModel!!
        }
        return state
    }

    fun onPause() {
        compassValueChangeEmitter.unregisterListener()
        currentLocationEmitter.unregisterRequestLocationUpdates()
    }

    override fun onShowCoordinatesInputClick() {
        compassView.showDestinationLocationInput()
    }

    override fun onHideCoordinatesInputClick() {
        compassView.hideDestinationLocationInput()
    }

    override fun onLocationInputChanged() {
        provideLocationNameUseCase.buildUseCaseObservable(getLatLngFromView())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { basicLocationData: BasicLocationData? ->
                    if (basicLocationData != null) {
                        viewModel?.countryName = basicLocationData.countryName
                        viewModel?.cityName = basicLocationData.cityName
                    }
                    updateDistance()
                }
                .subscribe()
    }

    private fun updateDistance() {
        computeDistanceUseCase.buildUseCaseObservable(getLatLngFromView())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { distance: Float ->
                    viewModel?.distanceToDestination = distance.formatToDistanceReadable()
                }
                .subscribe()
    }

    private fun getLatLngFromView(): LatLng? {
        return try {
            LatLng(viewModel!!.latCoordinate.toDouble(), viewModel!!.lngCoordinate.toDouble())
        } catch (e: NumberFormatException) {
            null
        }
    }
}