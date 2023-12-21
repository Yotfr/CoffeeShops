package ru.yotfr.sevenwindstestapp.domain.model

import java.lang.Math.atan2
import java.lang.Math.cos
import java.lang.Math.sin
import java.lang.Math.sqrt

data class CoordinatesModel(
    val latitude: Double,
    val longitude: Double
) {
    fun distanceBetweenCoordinatesKilometers(coordinatesModel: CoordinatesModel): Double {
        val earthRadius = 6371 // Радиус Земли в километрах

        val dLat = Math.toRadians(coordinatesModel.latitude - latitude)
        val dLon = Math.toRadians(coordinatesModel.longitude - longitude)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(latitude)) * cos(Math.toRadians(coordinatesModel.latitude)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }
}
