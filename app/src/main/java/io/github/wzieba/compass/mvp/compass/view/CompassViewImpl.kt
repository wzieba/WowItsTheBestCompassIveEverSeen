package io.github.wzieba.compass.mvp.compass.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import io.github.wzieba.compass.databinding.ActivityCompassBinding
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.mvp.compass.view.viewmodel.CompassViewModel
import javax.inject.Inject

@ActivityScope
class CompassViewImpl @Inject constructor(activityContext: Context) : CompassView {
    private val binding: ActivityCompassBinding = ActivityCompassBinding.inflate(LayoutInflater.from(activityContext))

    override fun asView(): View = binding.root

    override fun setViewModel(viewModel: CompassViewModel) {
        binding.viewModel = viewModel
    }

}