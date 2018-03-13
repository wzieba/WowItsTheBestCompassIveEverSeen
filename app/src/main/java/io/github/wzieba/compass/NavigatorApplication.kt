package io.github.wzieba.compass

import android.app.Application
import io.github.wzieba.compass.di.ApplicationComponent
import io.github.wzieba.compass.di.ApplicationModule
import io.github.wzieba.compass.di.DaggerApplicationComponent


class NavigatorApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }

    companion object {
        lateinit var instance: NavigatorApplication
            private set
    }
}