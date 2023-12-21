package ru.yotfr.sevenwindstestapp.presentation.map.state

import ru.yotfr.sevenwindstestapp.domain.model.CoordinatesModel
import ru.yotfr.sevenwindstestapp.domain.model.LocationModel

data class MapScreenState(
    val isLoading: Boolean = false,
    val locations: List<LocationModel> = emptyList(),
    val currentCoordinates: CoordinatesModel = CoordinatesModel(
        latitude = 55.7522, longitude = 37.6156
    )
)
