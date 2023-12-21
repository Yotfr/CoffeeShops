package ru.yotfr.sevenwindstestapp.domain.usecase

import ru.yotfr.sevenwindstestapp.domain.common.DataState
import ru.yotfr.sevenwindstestapp.domain.locationprovider.LocationProvider
import ru.yotfr.sevenwindstestapp.domain.model.CoordinatesModel
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationProvider: LocationProvider
) {
    suspend operator fun invoke(): DataState<CoordinatesModel> {
        return locationProvider.getCurrentCoordinates()
    }
}