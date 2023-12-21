package ru.yotfr.sevenwindstestapp.domain.tokenstorage

import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel

interface TokenStorage {

    suspend fun updateToken(tokenModel: TokenModel): DataState<Unit>
    suspend fun dropToken(): DataState<Unit>
    suspend fun getToken(): DataState<TokenModel?>
}