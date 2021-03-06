package io.github.wzieba.compass.android

import android.databinding.InverseMethod
import android.location.Location

class DataBindingConversionMethods {

    companion object {
        @InverseMethod("toStringValue")
        @JvmStatic
        fun toDoubleValue(value: String): Double =
                if (value.isEmpty() || value == "-") {
                    0.0
                } else {
                    value.toDouble()
                }

        @JvmStatic
        fun toStringValue(value: Double): String = value.toString()

    }
}
