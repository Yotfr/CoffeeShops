package ru.yotfr.sevenwindstestapp.presentation.map.event

import com.yandex.mapkit.geometry.Point

sealed interface MapEvent {
    data class PointClicked(val point: Point) : MapEvent
}