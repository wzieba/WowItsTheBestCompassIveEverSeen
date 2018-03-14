package io.github.wzieba.compass.mvp.compass.presenter

import io.github.wzieba.compass.data.compass.OnCompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.mvp.compass.view.CompassView
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel
import javax.inject.Inject

@ActivityScope
class CompassPresenter @Inject constructor(
        val compassView: CompassView,
        onCompassValueChangeEmitter: OnCompassValueChangeEmitter
) {

    private val viewModel = CompassViewModel()

    init {
        onCompassValueChangeEmitter.asObservable()
        compassView.setViewModel(viewModel)
    }
}