package ru.yotfr.sevenwindstestapp.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    val token: String,
    @SerializedName("tokenLifetime")
    val expires: Long
)
