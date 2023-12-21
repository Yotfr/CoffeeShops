package ru.yotfr.sevenwindstestapp.data.repository

import ru.yotfr.sevenwindstestapp.data.datasource.remote.api.CoffeeApi
import ru.yotfr.sevenwindstestapp.data.datasource.remote.utils.AuthHeaderProvider
import ru.yotfr.sevenwindstestapp.data.mappers.mapToLocationModel
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.errorCatchingValue
import ru.yotfr.sevenwindstestapp.domain.model.LocationModel
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel
import ru.yotfr.sevenwindstestapp.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val coffeeApi: CoffeeApi,
    private val authHeaderProvider: AuthHeaderProvider
) : LocationRepository {

    override suspend fun getAvailableLocations(
        tokenModel: TokenModel
    ): DataState<List<LocationModel>> {
        return errorCatchingValue {
            coffeeApi.getLocations(
                authHeaders = authHeaderProvider.getAuthHeaders(
                    accessToken = tokenModel.token
                )
            ).map { it.mapToLocationModel() }
        }
    }
}