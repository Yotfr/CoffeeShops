package ru.yotfr.sevenwindstestapp.presentation.locations.state

import ru.yotfr.sevenwindstestapp.domain.model.LocationModel

data class LocationsScreenState(
    val isLoading: Boolean = false,
    val locations: List<LocationModel> = emptyList()
)
