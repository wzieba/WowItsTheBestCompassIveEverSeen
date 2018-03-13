package io.github.wzieba.compass.model

import android.hardware.SensorEvent


class CompassIndication {


    companion object {
        fun map(sensorEvent: SensorEvent) = CompassIndication()
    }
}