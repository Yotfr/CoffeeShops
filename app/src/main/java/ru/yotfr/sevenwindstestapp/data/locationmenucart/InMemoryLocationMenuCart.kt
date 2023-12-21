package ru.yotfr.sevenwindstestapp.data.locationmenucart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import ru.yotfr.sevenwindstestapp.domain.locationmenucart.LocationMenuCart
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel

// Корзина пользователя для конкретной локации, инжектится как синглтон, реализация in-memory DB
class InMemoryLocationMenuCart : LocationMenuCart {

    private val cart = MutableStateFlow<Map<Int, List<LocationMenuCartItemModel>>>(emptyMap())

    override fun getCartForLocation(locationId: Int): Flow<List<LocationMenuCartItemModel>> {
        return cart.map { it.getOrDefault(locationId, emptyList()) }
    }

    override suspend fun updateItemInCart(item: LocationMenuModel, newCount: Int) {
        cart.value = cart.last().mapValues { entry ->
            val itemToRemove = entry.value.find { it.item == item }
            if (itemToRemove != null) {
                // Элемент уже есть в списке
                if (newCount == 0) {
                    // Убираем элемент из списка
                    entry.value.filter { it.item != item }
                } else {
                    // Изменяем количество выбранного элемента в корзине
                    entry.value.map { cartItem ->
                        if (cartItem.item == item) {
                            cartItem.copy(
                                count = newCount
                            )
                        } else {
                            cartItem
                        }
                    }
                }
            } else {
                // Элемента еще нет в списке, вставляем
                entry.value.plus(
                    LocationMenuCartItemModel(
                        item = item,
                        count = newCount
                    )
                )
            }
        }
    }
}