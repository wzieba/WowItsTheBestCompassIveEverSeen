package io.github.wzieba.compass.domain.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.github.wzieba.compass.di.ActivityScope
import io.github.wzieba.compass.model.CompassIndication
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/*
http://developer.samsung.com/technical-doc/view.do;jsessionid=16E160A31A4D4DB48D70BE5B702EF5C3?v=T000000114
 */

@ActivityScope
class CompassValueChangeEmitter @Inject constructor(
        private val sensorManager: SensorManager,
        private val notifier: PublishProcessor<CompassIndication>
) : SensorEventListener {

    private var gravity: FloatArray? = null
    private var geoMagnetic: FloatArray? = null

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //no-op
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values
        }
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geoMagnetic = event.values
        }
        if (gravity != null && geoMagnetic != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            val success = SensorManager.getRotationMatrix(R, I, gravity, geoMagnetic)

            if (success) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation);
                val azimuth = orientation[0]
                notifier.onNext(CompassIndication((-azimuth * 360 / (2 * 3.14159f)).toInt()))
            }
        }
    }

    fun registerListener() {
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI
        )
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    fun asObservable(): Flowable<CompassIndication> {
        return notifier.onBackpressureLatest()
                .onBackpressureDrop()
                .subscribeOn(Schedulers.io())
    }
}