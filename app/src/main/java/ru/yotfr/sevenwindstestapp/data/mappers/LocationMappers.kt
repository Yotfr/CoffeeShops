package ru.yotfr.sevenwindstestapp.data.mappers

import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.LocationDTO
import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.PointDTO
import ru.yotfr.sevenwindstestapp.domain.model.CoordinatesModel
import ru.yotfr.sevenwindstestapp.domain.model.LocationModel

fun LocationDTO.mapToLocationModel(): LocationModel =
    LocationModel(
        id = id,
        name = name,
        coordinates = point.mapToCoordinatesModel(),
        distanceFromYou = null
    )

fun PointDTO.mapToCoordinatesModel(): CoordinatesModel =
    CoordinatesModel(
        latitude = latitude,
        longitude = longitude
    )