package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.ErrorCause
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel
import ru.yotfr.sevenwindstestapp.domain.repository.LocationMenuRepository
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class GetLocationMenuUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val locationMenuRepository: LocationMenuRepository
) {

    operator fun invoke(locationId: Int): Flow<DataState<List<LocationMenuModel>>> = flow {
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
                emit(
                    locationMenuRepository.getLocationMenu(
                        tokenModel = token,
                        locationId = locationId
                    )
                )
            }
        }
    }
}