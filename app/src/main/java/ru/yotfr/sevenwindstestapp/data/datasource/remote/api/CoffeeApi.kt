package ru.yotfr.sevenwindstestapp.data.datasource.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.AuthRequest
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.LocationDTO
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.LocationMenuDTO
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.TokenDTO
import ru.yotfr.sevenwindstestapp.data.datasource.remote.utils.AuthHeaders

interface CoffeeApi {
    @POST("auth/register")
    suspend fun registerUser(
        @Body authRequest: AuthRequest
    ): TokenDTO

    @POST("auth/login")
    suspend fun authorizeUser(
        @Body authRequest: AuthRequest
    ): TokenDTO

    @GET("locations")
    suspend fun getLocations(
        @HeaderMap authHeaders: AuthHeaders
    ): List<LocationDTO>

    @GET("location/{id}/menu")
    suspend fun getLocationMenu(
        @HeaderMap authHeaders: AuthHeaders,
        @Path("id") id: Int
    ): List<LocationMenuDTO>
}