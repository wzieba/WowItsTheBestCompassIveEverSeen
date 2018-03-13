package io.github.wzieba.compass.mvp.compass.di

import dagger.Component
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.di.ApplicationComponent
import io.github.wzieba.compass.mvp.compass.CompassActivity

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(CompassModule::class))
interface CompassComponent {

    fun inject(compassActivity: CompassActivity)
}