package io.github.wzieba.compass.di

import dagger.Module
import dagger.Provides
import io.github.wzieba.compass.NavigatorApplication

@Module
class ApplicationModule(private val navigatorApplication: NavigatorApplication) {


    @Provides
    fun provideApplication() = navigatorApplication
}