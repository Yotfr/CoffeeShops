package ru.yotfr.sevenwindstestapp.presentation.locationmenu.state

import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel

data class LocationMenuScreenState(
    val isLoading: Boolean = false,
    val locationMenu: List<LocationMenuModel> = emptyList()
)
