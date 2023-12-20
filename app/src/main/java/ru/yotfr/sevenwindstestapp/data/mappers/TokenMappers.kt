package ru.yotfr.sevenwindstestapp.data.mappers

import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.TokenDTO
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel

fun TokenDTO.mapToTokenModel(): TokenModel =
    TokenModel(
        token = token
    )