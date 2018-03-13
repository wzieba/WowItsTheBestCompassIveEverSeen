package io.github.wzieba.compass.mvp.compass.presenter

import io.github.wzieba.compass.data.compass.OnCompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class CompassPresenter @Inject constructor(onCompassValueChangeEmitter: OnCompassValueChangeEmitter) {

    init {
        onCompassValueChangeEmitter.asObservable()
    }
}