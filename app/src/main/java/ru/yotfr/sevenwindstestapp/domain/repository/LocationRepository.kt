package ru.yotfr.sevenwindstestapp.domain.repository

import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.LocationModel
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel

interface LocationRepository {
    suspend fun getAvailableLocations(
        tokenModel: TokenModel
    ): DataState<List<LocationModel>>
}