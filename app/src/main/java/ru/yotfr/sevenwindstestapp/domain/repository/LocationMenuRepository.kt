package ru.yotfr.sevenwindstestapp.domain.repository

import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel

interface LocationMenuRepository {
    suspend fun getLocationMenu(
        tokenModel: TokenModel,
        locationId: Int
    ): DataState<List<LocationMenuModel>>
}