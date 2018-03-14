package io.github.wzieba.compass.mvp.compass.view

import android.content.Context
import android.support.constraint.ConstraintSet
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import io.github.wzieba.compass.R
import io.github.wzieba.compass.databinding.ActivityCompassBinding
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel
import javax.inject.Inject

@ActivityScope
class CompassViewImpl @Inject constructor(private val activityContext: Context) : CompassView {
    private val binding: ActivityCompassBinding = ActivityCompassBinding.inflate(LayoutInflater.from(activityContext))

    override fun asView(): View = binding.root

    override fun setViewModel(viewModel: CompassViewModel) {
        binding.viewModel = viewModel
    }

    override fun setListener(listener: CompassView.Listener) {
        binding.listener = listener
    }

    override fun showDestinationLocationInput(latLng: LatLng) {
        val hiddenInputConstraintSet = ConstraintSet()
        val visibleInputConstraintSet = ConstraintSet()

        hiddenInputConstraintSet.clone(binding.compassConstraintLayout)
        visibleInputConstraintSet.clone(activityContext, R.layout.activity_compass_location_input)

        TransitionManager.beginDelayedTransition(binding.compassConstraintLayout)
        visibleInputConstraintSet.applyTo(binding.compassConstraintLayout)
    }

    override fun hideDestinationLocationInput() {
        val visibleInputConstraintSet = ConstraintSet()
        val hiddenConstraintSet = ConstraintSet()

        visibleInputConstraintSet.clone(binding.compassConstraintLayout)
        hiddenConstraintSet.clone(activityContext, R.layout.activity_compass)

        TransitionManager.beginDelayedTransition(binding.compassConstraintLayout)
        hiddenConstraintSet.applyTo(binding.compassConstraintLayout)
    }

}