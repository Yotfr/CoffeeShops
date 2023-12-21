package ru.yotfr.sevenwindstestapp.domain.locationmenucart

import kotlinx.coroutines.flow.Flow
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuModel

/**
 * [LocationMenuCart] - интерфейс для работы с корзиной пользователя в конкретной локации
 */
interface LocationMenuCart {

    /**
     * [getCartForLocation] возвращает флоу актуальных позиций в корзине для конкретной локации.
     * @param[locationId] - айди локации для которой нужно полуить позиции в корзине
     */
    fun getCartForLocation(locationId: Int): Flow<List<LocationMenuCartItemModel>>

    /**
     * [updateItemInCart] изменяет количество элементов в корзине. Если передать 0 - айтем удалится из
     * корзины.
     *
     * @param[item] - элемент, количество которого необходимо изменить
     * @param[newCount] - новое количество элемента [item]
     */
    suspend fun  updateItemInCart(item: LocationMenuModel, newCount: Int)
}