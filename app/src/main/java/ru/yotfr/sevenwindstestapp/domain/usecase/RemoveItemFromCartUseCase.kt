package ru.yotfr.sevenwindstestapp.domain.usecase

import ru.yotfr.sevenwindstestapp.domain.cartmanager.CartManager
import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel
import javax.inject.Inject

class RemoveItemFromCartUseCase @Inject constructor(
    private val cartManager: CartManager
) {
    operator fun invoke(locationId: Int, item: CartItemModel) {
        cartManager.removeItem(
            locationId = locationId,
            item = item
        )
    }
}