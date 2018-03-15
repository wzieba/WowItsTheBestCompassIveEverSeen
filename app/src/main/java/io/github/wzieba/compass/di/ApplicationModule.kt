package io.github.wzieba.compass.di

import dagger.Module
import dagger.Provides
import io.github.wzieba.compass.NavigatorApplication
import io.github.wzieba.compass.domain.location.LocationRepository
import io.github.wzieba.compass.domain.location.LocationRepositoryImpl

@Module
class ApplicationModule(private val navigatorApplication: NavigatorApplication) {

    @Provides
    fun provideApplication() = navigatorApplication

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository {
            return locationRepositoryImpl
        }
    }
}