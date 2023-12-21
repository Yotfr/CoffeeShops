package ru.yotfr.sevenwindstestapp.presentation.navigation.screens

const val LOCATION_ID_ARGUMENT_KEY = "id"

sealed class MainScreen(val route: String) {
    data object Locations : MainScreen(route = "locations")
    data object Map : MainScreen(route = "map")
    data object Menu : MainScreen(route = "menu/{$LOCATION_ID_ARGUMENT_KEY}") {
        fun passId(id: Int): String {
            return this.route.replace(
                oldValue = "{$LOCATION_ID_ARGUMENT_KEY}",
                newValue = id.toString()
            )
        }
    }

    data object Payment : MainScreen(route = "payment/{$LOCATION_ID_ARGUMENT_KEY}") {
        fun passId(id: Int): String {
            return this.route.replace(
                oldValue = "{$LOCATION_ID_ARGUMENT_KEY}",
                newValue = id.toString()
            )
        }
    }
}
