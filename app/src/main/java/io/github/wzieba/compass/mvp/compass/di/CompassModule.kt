package io.github.wzieba.compass.mvp.compass.di


import dagger.Module
import dagger.Provides
import io.github.wzieba.compass.data.compass.OnCompassValueChangeEmitter
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.CompassIndication
import io.github.wzieba.compass.mvp.compass.CompassActivity
import io.reactivex.subjects.PublishSubject

@Module
class CompassModule(private val compassActivity: CompassActivity) {

    @Provides
    @ActivityScope
    fun providePublishSubject(): PublishSubject<CompassIndication> {
        return PublishSubject.create<CompassIndication>()
    }

    @Provides
    @ActivityScope
    fun provideCompassValueChangeEmitter(
            publishSubject: PublishSubject<CompassIndication>
    ): OnCompassValueChangeEmitter {
        return OnCompassValueChangeEmitter(publishSubject)
    }
}
