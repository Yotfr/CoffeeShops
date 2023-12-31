package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.yotfr.sevenwindstestapp.domain.cartmanager.CartManager
import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel
import javax.inject.Inject

class GetCartForLocationUseCase @Inject constructor(
    private val cartManager: CartManager
) {
    operator fun invoke(locationId: Int): Flow<List<CartItemModel>> {
        return cartManager.getCart(locationId)
    }
}