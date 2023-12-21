package ru.yotfr.sevenwindstestapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.mapType
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(authModel: AuthModel): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading())
        emit(
            authRepository.registerUser(
                authModel = authModel
            ).mapType { }
        )
    }
}