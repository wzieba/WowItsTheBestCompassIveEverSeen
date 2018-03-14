package io.github.wzieba.compass.android

import android.databinding.InverseMethod

class DataBindingConversionMethods {

    companion object {
        @InverseMethod("toStringValue")
        @JvmStatic
        fun toDoubleValue(value: String): Double {
            return value.toDouble()
        }

        @JvmStatic
        fun toStringValue(value: Double): String {
            return value.toString()
        }
    }
}
