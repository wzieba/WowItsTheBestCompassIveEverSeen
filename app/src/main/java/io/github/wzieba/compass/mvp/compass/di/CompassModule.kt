package io.github.wzieba.compass.mvp.compass.di


import android.content.Context
import android.hardware.SensorManager
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides
import io.github.wzieba.compass.domain.compass.CompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.domain.location.LocationRepository
import io.github.wzieba.compass.domain.location.LocationRepositoryImpl
import io.github.wzieba.compass.model.CompassIndication
import io.github.wzieba.compass.model.LatLng
import io.github.wzieba.compass.mvp.compass.CompassActivity
import io.github.wzieba.compass.mvp.compass.view.CompassView
import io.github.wzieba.compass.mvp.compass.view.CompassViewImpl
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.PublishSubject

@Module
class CompassModule(private val compassActivity: CompassActivity) {

    @Provides
    @ActivityScope
    fun providePublishSubject(): PublishSubject<LatLng> {
        return PublishSubject.create()
    }

    @Provides
    @ActivityScope
    fun provideFusecLocationProviderClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(compassActivity)
    }

    @Provides
    @ActivityScope
    fun provideRxPermissons(): RxPermissions = RxPermissions(compassActivity)

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
        @Provides
        @JvmStatic
        fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository {
            return locationRepositoryImpl
        }
        //https://google.github.io/dagger/faq.html#what-do-i-do-instead
    }
}
