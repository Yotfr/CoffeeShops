package ru.yotfr.sevenwindstestapp.domain.usecase

import ru.yotfr.sevenwindstestapp.domain.locationmenucart.LocationMenuCart
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel
import javax.inject.Inject

class UpdateItemCountInCartUseCase @Inject constructor(
    private val locationMenuCart: LocationMenuCart
) {
    suspend operator fun invoke(item: LocationMenuModel, newCount: Int) {
        locationMenuCart.updateItemInCart(
            item = item,
            newCount = newCount
        )
    }
}