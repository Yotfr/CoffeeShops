package ru.yotfr.sevenwindstestapp.data.mappers

import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.AuthRequest
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel

fun AuthModel.mapToAuthRequest(): AuthRequest =
    AuthRequest(
        login = login,
        password = password
    )