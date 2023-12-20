package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.common.DataState
import ru.yotfr.sevenwindstestapp.domain.common.onError
import ru.yotfr.sevenwindstestapp.domain.common.onSuccess
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.repository.AuthRepository
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authRepository: AuthRepository
) {

    operator fun invoke(authModel: AuthModel): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading())
        authRepository.registerUser(
            authModel = authModel
        ).onError { message ->
            emit(
                DataState.Error(
                    message = message
                )
            )
        }.onSuccess { tokenModel ->
            emit(
                tokenStorage.updateToken(
                    tokenModel = tokenModel
                )
            )
        }
    }
}