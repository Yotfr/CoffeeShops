package ru.yotfr.sevenwindstestapp.domain.repository

import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel

interface AuthRepository {
    suspend fun authorizeUser(authModel: AuthModel): DataState<TokenModel>
    suspend fun registerUser(authModel: AuthModel): DataState<TokenModel>
}