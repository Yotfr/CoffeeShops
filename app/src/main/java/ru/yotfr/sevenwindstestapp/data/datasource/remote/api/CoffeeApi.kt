package ru.yotfr.sevenwindstestapp.data.datasource.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.AuthRequest
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.TokenDTO

interface CoffeeApi {
    @POST("auth/register")
    suspend fun registerUser(
        @Body authRequest: AuthRequest
    ): TokenDTO

    @POST("auth/login")
    suspend fun authorizeUser(
        @Body authRequest: AuthRequest
    ): TokenDTO
}