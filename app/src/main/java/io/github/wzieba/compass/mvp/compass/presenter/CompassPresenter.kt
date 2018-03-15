package io.github.wzieba.compass.mvp.compass.presenter

import io.github.wzieba.compass.domain.ComputeDistanceUseCase
import io.github.wzieba.compass.domain.ProvideCurrentLocationUseCase
import io.github.wzieba.compass.domain.ProvideLocationNameUseCase
import io.github.wzieba.compass.domain.compass.CompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.formatToDistanceReadable
import io.github.wzieba.compass.model.BasicLocationData
import io.github.wzieba.compass.model.CompassIndication
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.mvp.compass.view.CompassView
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@ActivityScope
class CompassPresenter @Inject constructor(
        private val compassView: CompassView,
        private val provideLocationNameUseCase: ProvideLocationNameUseCase,
        private val compassValueChangeEmitter: CompassValueChangeEmitter,
        private val provideCurrentLocationUseCase: ProvideCurrentLocationUseCase,
        private val computeDistanceUseCase: ComputeDistanceUseCase
) : CompassView.Listener {

    private val viewModel = CompassViewModel()

    init {
        compassValueChangeEmitter.asObservable()
        compassView.setViewModel(viewModel)
        compassView.setListener(this)
        compassValueChangeEmitter.asObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { compassIndication: CompassIndication ->
                    viewModel.arrowRotation = compassIndication.degree.toFloat()
                }
                .subscribe()
        provideCurrentLocationUseCase.asObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { currentLocation: LatLng ->
                    computeDistanceUseCase.buildUseCaseObservable(LatLng(viewModel.latCoordinate, viewModel.lngCoordinate))
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .doOnNext { distance: Float ->
                                viewModel.distanceToDestination = distance.formatToDistanceReadable()
                            }
                            .subscribe()
                }
                .subscribe()
    }

    override fun onResume() {
        compassValueChangeEmitter.registerListener()
        provideCurrentLocationUseCase.registerRequestLocationUpdates()
    }

    override fun onPause() {
        compassValueChangeEmitter
                .asObservable()
                .unsubscribeOn(AndroidSchedulers.mainThread())
        compassValueChangeEmitter.unregisterListener()
        provideCurrentLocationUseCase.unregisterRequestLocationUpdates()
    }

    override fun onShowCoordinatesInputClick() {
        compassView.showDestinationLocationInput()
    }

    override fun onHideCoordinatesInputClick() {
        compassView.hideDestinationLocationInput()
    }

    override fun onLocationInputChanged() {
        provideLocationNameUseCase.buildUseCaseObservable(LatLng(viewModel.latCoordinate, viewModel.lngCoordinate))
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { basicLocationData: BasicLocationData? ->
                    if (basicLocationData != null) {
                        viewModel.countryName = basicLocationData.countryName
                        viewModel.cityName = basicLocationData.cityName
                    }

                    computeDistanceUseCase.buildUseCaseObservable(LatLng(viewModel.latCoordinate, viewModel.lngCoordinate))
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .doOnNext { distance: Float ->
                                viewModel.distanceToDestination = distance.formatToDistanceReadable()
                            }
                            .subscribe()
                }
                .subscribe()
    }
}