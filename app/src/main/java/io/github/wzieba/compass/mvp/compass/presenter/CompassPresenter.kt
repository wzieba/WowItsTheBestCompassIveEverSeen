package io.github.wzieba.compass.mvp.compass.presenter

import io.github.wzieba.compass.data.ProvideLocationNameUseCase
import io.github.wzieba.compass.data.compass.OnCompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.BasicLocationData
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.mvp.compass.view.CompassView
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@ActivityScope
class CompassPresenter @Inject constructor(
        private val compassView: CompassView,
        private val provideLocationNameUseCase: ProvideLocationNameUseCase,
        onCompassValueChangeEmitter: OnCompassValueChangeEmitter
) : CompassView.Listener {
    private val viewModel = CompassViewModel()

    init {
        onCompassValueChangeEmitter.asObservable()
        compassView.setViewModel(viewModel)
        compassView.setListener(this)
    }

    override fun onShowCoordinatesInputClick() {
        compassView.showDestinationLocationInput(LatLng(0.0, 0.0))
        viewModel.isLocationInputVisible = viewModel.isLocationInputVisible.not()
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
                }
                .subscribe()
    }
}