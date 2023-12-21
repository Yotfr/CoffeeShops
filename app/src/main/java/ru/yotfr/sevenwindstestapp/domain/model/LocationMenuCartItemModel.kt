package ru.yotfr.sevenwindstestapp.domain.model

/**
 * [LocationMenuCartItemModel] - один айтем в корзине локации
 */
data class LocationMenuCartItemModel(
    val item: LocationMenuModel,
    val count: Int
)

