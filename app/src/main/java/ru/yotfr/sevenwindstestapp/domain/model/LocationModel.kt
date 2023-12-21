package ru.yotfr.sevenwindstestapp.domain.model

data class LocationModel(
    val id: Int,
    val name: String,
    val coordinates: CoordinatesModel,
    val distanceFromYou: Double?
) {
    fun withCalculatedDistance(currentCoordinates: CoordinatesModel): LocationModel =
        LocationModel(
            id = id,
            name = name,
            coordinates = coordinates,
            distanceFromYou = coordinates.distanceBetweenCoordinatesKilometers(
                coordinatesModel = currentCoordinates
            )
        )
}
