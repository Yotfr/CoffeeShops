package ru.yotfr.sevenwindstestapp.data.mappers

import ru.yotfr.sevenwindstestapp.data.datasource.remote.model.LocationMenuDTO
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel

fun LocationMenuDTO.mapToLocationMenuModel(): LocationMenuModel =
    LocationMenuModel(
        id = id,
        name = name,
        imageUrl = imageURL,
        price = price
    )