package ru.yotfr.sevenwindstestapp.presentation.locationmenu.event

import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel

sealed interface LocationMenuEvent {
    data class EnteredScreen(val locationId: Int) : LocationMenuEvent
    data class IncreaseItemCartCount(val item: LocationMenuCartItemModel) : LocationMenuEvent
    data class DecreaseItemCartCount(val item: LocationMenuCartItemModel) : LocationMenuEvent
}