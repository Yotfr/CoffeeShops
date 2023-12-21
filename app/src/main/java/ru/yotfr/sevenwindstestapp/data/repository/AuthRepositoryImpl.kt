package ru.yotfr.sevenwindstestapp.data.repository

import ru.yotfr.sevenwindstestapp.data.datasource.remote.api.CoffeeApi
import ru.yotfr.sevenwindstestapp.data.mappers.mapToAuthRequest
import ru.yotfr.sevenwindstestapp.data.mappers.mapToTokenModel
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.errorCatchingValue
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel
import ru.yotfr.sevenwindstestapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val coffeeApi: CoffeeApi
) : AuthRepository {

    override suspend fun authorizeUser(authModel: AuthModel): DataState<TokenModel> {
        return errorCatchingValue {
            coffeeApi.authorizeUser(
                authRequest = authModel.mapToAuthRequest()
            ).mapToTokenModel()
        }
    }

    override suspend fun registerUser(authModel: AuthModel): DataState<TokenModel> {
        return errorCatchingValue {
            coffeeApi.registerUser(
                authRequest = authModel.mapToAuthRequest()
            ).mapToTokenModel()
        }
    }
}