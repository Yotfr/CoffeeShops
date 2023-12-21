package ru.yotfr.sevenwindstestapp.presentation.cart.state

import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel

data class CartScreenState(
    val cartItems: List<LocationMenuCartItemModel> = emptyList()
)