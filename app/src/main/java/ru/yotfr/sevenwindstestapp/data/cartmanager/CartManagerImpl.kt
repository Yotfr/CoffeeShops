package ru.yotfr.sevenwindstestapp.data.cartmanager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import ru.yotfr.sevenwindstestapp.domain.cartmanager.CartManager
import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel
import javax.inject.Inject

class CartManagerImpl @Inject constructor() : CartManager {

    private val cart =
        MutableStateFlow<Map<Int, List<CartItemModel>>>(emptyMap())

    override fun getCart(locationId: Int): Flow<List<CartItemModel>> {
        return cart.map { it.getOrDefault(locationId, emptyList()) }
    }

    override fun addItem(locationId: Int, item: CartItemModel) {
        if (cart.value.containsKey(locationId)) {
            var cartForLocation = cart.value[locationId] ?: return
            if (cartForLocation.find { it.menuModel.id == item.menuModel.id } == null) {
                cartForLocation = cartForLocation.plus(item)
            }
            cart.value += locationId to cartForLocation.map { currentCartModel ->
                if (currentCartModel.menuModel.id == item.menuModel.id) currentCartModel.copy(
                    count = currentCartModel.count + 1
                ) else currentCartModel
            }
        } else {
            cart.value += locationId to listOf(item.copy(count = 1))
        }
    }

    override fun removeItem(locationId: Int, item: CartItemModel) {
        if (cart.value.containsKey(locationId)) {
            val cartForLocation = cart.value[locationId] ?: return
            cart.value += locationId to cartForLocation.map {
                if (it.menuModel.id == item.menuModel.id) it.copy(
                    count = it.count - 1
                ) else it
            }
            val cartForLocationAfter = cart.value[locationId] ?: return
            cart.value += locationId to cartForLocationAfter.filter { it.count != 0 }
        } else {
            cart.value += locationId to listOf(item.copy(count = 1))
        }
    }
}