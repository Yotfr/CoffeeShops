package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.repository.AuthRepository
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class AuthorizeUserUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authRepository: AuthRepository
) {

    operator fun invoke(authModel: AuthModel): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading())
        authRepository.authorizeUser(
            authModel = authModel
        ).onError { message, cause ->
            emit(
                DataState.Error(
                    message,
                    cause
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