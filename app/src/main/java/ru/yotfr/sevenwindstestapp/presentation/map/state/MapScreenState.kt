package ru.yotfr.sevenwindstestapp.presentation.map.state

import ru.yotfr.sevenwindstestapp.domain.model.LocationModel

data class MapScreenState(
    val isLoading: Boolean = false,
    val locations: List<LocationModel> = emptyList()
)
