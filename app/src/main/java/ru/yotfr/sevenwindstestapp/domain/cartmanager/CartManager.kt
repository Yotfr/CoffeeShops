package ru.yotfr.sevenwindstestapp.domain.cartmanager

import kotlinx.coroutines.flow.Flow
import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel

interface CartManager {
    fun getCart(locationId: Int): Flow<List<CartItemModel>>
    fun addItem(locationId: Int, item: CartItemModel)
    fun removeItem(locationId: Int, item: CartItemModel)
}