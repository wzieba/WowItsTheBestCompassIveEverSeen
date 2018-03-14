package io.github.wzieba.compass.data.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.CompassIndication
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


@ActivityScope
class OnCompassValueChangeEmitter @Inject constructor(
        private val notifier: PublishSubject<CompassIndication>
) : SensorEventListener {

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //no-op
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        notifier.onNext(CompassIndication.map(sensorEvent))
        notifier.onComplete()
    }

    fun asObservable(): Observable<CompassIndication> {
        return notifier
    }
}