package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.yotfr.sevenwindstestapp.domain.locationmenucart.LocationMenuCart
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel
import javax.inject.Inject

class GetCartForLocationUseCase @Inject constructor(
    private val locationMenuCart: LocationMenuCart
) {
    operator fun invoke(locationId: Int): Flow<List<LocationMenuCartItemModel>> {
        return locationMenuCart.getCartForLocation(locationId = locationId)
    }
}