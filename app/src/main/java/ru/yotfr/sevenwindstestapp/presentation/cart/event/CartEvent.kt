package ru.yotfr.sevenwindstestapp.presentation.cart.event

import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuEvent

sealed interface CartEvent {
    data class EnteredScreen(val locationId: Int) : CartEvent
    data class IncreaseItemCartCount(val item: LocationMenuCartItemModel) : CartEvent
    data class DecreaseItemCartCount(val item: LocationMenuCartItemModel) : CartEvent
}