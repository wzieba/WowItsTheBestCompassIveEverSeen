package io.github.wzieba.compass.di

import dagger.Component
import io.github.wzieba.compass.NavigatorApplication
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(navigatorApplication: NavigatorApplication)
}