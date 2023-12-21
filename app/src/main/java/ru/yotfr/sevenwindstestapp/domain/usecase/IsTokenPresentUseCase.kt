package ru.yotfr.sevenwindstestapp.domain.usecase

import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.mapType
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class IsTokenPresentUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(): DataState<Boolean> {
        return tokenStorage.getToken().mapType {
            it != null
        }
    }
}