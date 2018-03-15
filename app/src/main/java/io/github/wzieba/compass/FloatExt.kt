package io.github.wzieba.compass


fun Float.formatToDistanceReadable(): String {
    return when {
        this < 1000 -> "${this.toInt()} m"
        else -> "${(this / 1000).toInt()} km"
    }
}

