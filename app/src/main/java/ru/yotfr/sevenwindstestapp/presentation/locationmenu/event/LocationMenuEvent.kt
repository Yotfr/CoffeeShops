package ru.yotfr.sevenwindstestapp.presentation.locationmenu.event

import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel

sealed interface LocationMenuEvent {
    data class EnteredScreen(val locationId: Int) : LocationMenuEvent
    data class IncreaseItemCartCount(val item: CartItemModel, val locationId: Int) : LocationMenuEvent
    data class DecreaseItemCartCount(val item: CartItemModel, val locationId: Int) : LocationMenuEvent
    data object PullRefresh : LocationMenuEvent
}