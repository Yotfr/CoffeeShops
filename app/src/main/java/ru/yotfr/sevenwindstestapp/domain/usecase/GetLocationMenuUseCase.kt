package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.common.DataState
import ru.yotfr.sevenwindstestapp.domain.common.onError
import ru.yotfr.sevenwindstestapp.domain.common.onSuccess
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
        tokenStorage.getToken().onError {
            emit(DataState.Error(it))
        }.onSuccess { token ->
            if (token == null) {
                //TODO: Сделать по уму
                emit(DataState.Error("Unauthorized"))
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