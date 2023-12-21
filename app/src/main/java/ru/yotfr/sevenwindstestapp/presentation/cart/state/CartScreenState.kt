package ru.yotfr.sevenwindstestapp.presentation.cart.state

import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel

data class CartScreenState(
    val cartItems: List<CartItemModel> = emptyList()
)