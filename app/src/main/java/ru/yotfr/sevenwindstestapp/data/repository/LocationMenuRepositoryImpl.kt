package ru.yotfr.sevenwindstestapp.data.repository

import ru.yotfr.sevenwindstestapp.data.datasource.remote.api.CoffeeApi
import ru.yotfr.sevenwindstestapp.data.datasource.remote.utils.AuthHeaderProvider
import ru.yotfr.sevenwindstestapp.data.mappers.mapToLocationMenuModel
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.errorCatchingValue
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel
import ru.yotfr.sevenwindstestapp.domain.repository.LocationMenuRepository
import javax.inject.Inject

class LocationMenuRepositoryImpl @Inject constructor(
    private val coffeeApi: CoffeeApi,
    private val authHeaderProvider: AuthHeaderProvider
) : LocationMenuRepository {
    override suspend fun getLocationMenu(
        tokenModel: TokenModel,
        locationId: Int
    ): DataState<List<LocationMenuModel>> {
        return errorCatchingValue {
            coffeeApi.getLocationMenu(
                authHeaders = authHeaderProvider.getAuthHeaders(
                    accessToken = tokenModel.token
                ),
                id = locationId
            ).map { it.mapToLocationMenuModel() }
        }
    }
}