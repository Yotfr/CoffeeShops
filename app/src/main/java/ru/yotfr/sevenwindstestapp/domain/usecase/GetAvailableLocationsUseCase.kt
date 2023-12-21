package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.common.DataState
import ru.yotfr.sevenwindstestapp.domain.common.onError
import ru.yotfr.sevenwindstestapp.domain.common.onSuccess
import ru.yotfr.sevenwindstestapp.domain.locationprovider.LocationProvider
import ru.yotfr.sevenwindstestapp.domain.model.LocationModel
import ru.yotfr.sevenwindstestapp.domain.repository.LocationRepository
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class GetAvailableLocationsUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val locationRepository: LocationRepository,
    private val locationProvider: LocationProvider
) {

    operator fun invoke(): Flow<DataState<List<LocationModel>>> = flow {
        emit(DataState.Loading())
        tokenStorage.getToken().onError {
            emit(DataState.Error(it))
        }.onSuccess { token ->
            if (token == null) {
                //TODO: Сделать по уму
                emit(DataState.Error("Unauthorized"))
            } else {
                locationRepository.getAvailableLocations(
                    tokenModel = token
                ).onError {
                    emit(DataState.Error(it))
                }.onSuccess { locations ->
                    locationProvider.getCurrentCoordinates().onError {
                        emit(DataState.Error(it))
                        emit(DataState.Success(data = locations))
                    }.onSuccess { currentCoordinates ->
                        val locationWithDistances = locations.map {
                            it.withCalculatedDistance(
                                currentCoordinates = currentCoordinates
                            )
                        }
                        emit(DataState.Success(data = locationWithDistances))
                    }
                }
            }
        }
    }
}