package io.github.wzieba.compass.android

import android.databinding.InverseMethod

class DataBindingConversionMethods {

    companion object {
        @InverseMethod("toStringValue")
        @JvmStatic
        fun toDoubleValue(value: String): Double =
                if (value.isEmpty()) {
                    0.0
                } else {
                    value.toDouble()
                }

        @JvmStatic
        fun toStringValue(value: Double): String = value.toString()

    }
}
