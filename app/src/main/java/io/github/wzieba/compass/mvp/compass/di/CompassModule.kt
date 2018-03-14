package io.github.wzieba.compass.mvp.compass.di


import android.content.Context
import android.hardware.SensorManager
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import io.github.wzieba.compass.data.compass.CompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.CompassIndication
import io.github.wzieba.compass.mvp.compass.CompassActivity
import io.github.wzieba.compass.mvp.compass.view.CompassView
import io.github.wzieba.compass.mvp.compass.view.CompassViewImpl
import io.reactivex.processors.PublishProcessor

@Module
class CompassModule(private val compassActivity: CompassActivity) {

    @Provides
    @ActivityScope
    fun provideSensorManager(): SensorManager {
        return compassActivity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @Provides
    @ActivityScope
    fun provideGeocoder(): Geocoder {
        return Geocoder(compassActivity)
    }

    @Provides
    @ActivityScope
    fun provideActivityContext(): Context {
        return compassActivity
    }

    @Provides
    @ActivityScope
    fun providePublishProcessor(): PublishProcessor<CompassIndication> {
        return PublishProcessor.create<CompassIndication>()
    }

    @Provides
    @ActivityScope
    fun provideCompassValueChangeEmitter(
            publishProcessor: PublishProcessor<CompassIndication>,
            sensorManager: SensorManager
    ): CompassValueChangeEmitter {
        return CompassValueChangeEmitter(sensorManager, publishProcessor)
    }

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideCompassView(compassViewImpl: CompassViewImpl): CompassView {
            return compassViewImpl
        }
        //https://google.github.io/dagger/faq.html#what-do-i-do-instead
    }
}
