package io.github.wzieba.compass.mvp.compass.view

import android.view.View
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel

interface CompassView {
    fun asView(): View
    fun setViewModel(viewModel: CompassViewModel)
}