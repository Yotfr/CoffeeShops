package ru.yotfr.sevenwindstestapp.domain.locationprovider

import ru.yotfr.sevenwindstestapp.domain.common.DataState
import ru.yotfr.sevenwindstestapp.domain.model.CoordinatesModel

interface LocationProvider {
    suspend fun getCurrentCoordinates(): DataState<CoordinatesModel>
}