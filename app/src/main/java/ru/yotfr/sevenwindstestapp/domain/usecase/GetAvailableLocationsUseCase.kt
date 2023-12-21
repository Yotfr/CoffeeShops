package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.locationprovider.LocationProvider
import ru.yotfr.sevenwindstestapp.domain.model.ErrorCause
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
        tokenStorage.getToken().onError { message, cause ->
            emit(DataState.Error(message, cause))
        }.onSuccess { token ->
            if (token == null) {
                emit(
                    DataState.Error(
                        null,
                        ErrorCause.Unauthorized
                    )
                )
            } else {
                locationRepository.getAvailableLocations(
                    tokenModel = token
                ).onError { message, cause ->
                    emit(
                        DataState.Error(
                            message, cause
                        )
                    )
                }.onSuccess { locations ->
                    locationProvider.getCurrentCoordinates().onError { message, cause ->
                        emit(DataState.Error(message, cause))
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