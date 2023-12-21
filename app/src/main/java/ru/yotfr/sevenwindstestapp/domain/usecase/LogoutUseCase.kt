package ru.yotfr.sevenwindstestapp.domain.usecase

import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {

    suspend operator fun invoke() {
        tokenStorage.dropToken()
    }
}