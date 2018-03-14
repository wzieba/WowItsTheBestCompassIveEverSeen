package io.github.wzieba.compass.android.bindingadapters

import android.databinding.BindingAdapter
import io.github.wzieba.compass.android.view.CompassWidget

@BindingAdapter("arrowRotation")
fun CompassWidget.rotateArrowBinding(rotation: Float) {
    this.rotateArrow(rotation)
}