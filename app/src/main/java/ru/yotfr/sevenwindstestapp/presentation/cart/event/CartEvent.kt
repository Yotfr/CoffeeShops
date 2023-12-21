package ru.yotfr.sevenwindstestapp.presentation.cart.event

import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel

sealed interface CartEvent {
    data class EnteredScreen(val locationId: Int) : CartEvent
    data class IncreaseItemCartCount(val item: CartItemModel, val locationId: Int) :
        CartEvent
    data class DecreaseItemCartCount(val item: CartItemModel, val locationId: Int) :
        CartEvent
}