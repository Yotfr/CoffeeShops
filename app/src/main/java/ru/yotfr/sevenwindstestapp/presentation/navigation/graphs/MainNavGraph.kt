package ru.yotfr.sevenwindstestapp.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.yotfr.sevenwindstestapp.presentation.cart.screen.CartScreen
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.screen.LocationMenuScreen
import ru.yotfr.sevenwindstestapp.presentation.locations.screen.LocationsScreen
import ru.yotfr.sevenwindstestapp.presentation.map.screen.MapScreen
import ru.yotfr.sevenwindstestapp.presentation.navigation.screens.AuthScreen
import ru.yotfr.sevenwindstestapp.presentation.navigation.screens.LOCATION_ID_ARGUMENT_KEY
import ru.yotfr.sevenwindstestapp.presentation.navigation.screens.MainScreen
import java.lang.IllegalArgumentException

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = Graph.MAIN,
        startDestination = MainScreen.Locations.route
    ) {
        composable(
            route = MainScreen.Locations.route
        ) {
            LocationsScreen(
                navigateMapScreen = {
                    navController.navigate(MainScreen.Map.route)
                },
                navigateToLocationMenuScreen = { locationId ->
                    navController.navigate(MainScreen.Menu.passId(locationId))
                },
                logout = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(MainScreen.Locations.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = MainScreen.Map.route
        ) {
            MapScreen(
                navigateToLocationMenuScreen = { locationId ->
                    navController.navigate(MainScreen.Menu.passId(locationId))
                },
                navigateBack = {
                    navController.popBackStack()
                },
                logout = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(MainScreen.Map.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = MainScreen.Menu.route
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString(LOCATION_ID_ARGUMENT_KEY)?.toInt() ?:
            throw IllegalArgumentException("Navigated with wrong locationID. SHOULD NOT HAPPEN")
            LocationMenuScreen(
                locationId = locationId,
                navigateBack = { navController.popBackStack() },
                navigatePaymentScreen = { navController.navigate(MainScreen.Payment.passId(it)) },
                logout = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(MainScreen.Menu.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = MainScreen.Payment.route
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString(LOCATION_ID_ARGUMENT_KEY)?.toInt() ?:
            throw IllegalArgumentException("Navigated with wrong locationID. SHOULD NOT HAPPEN")
            CartScreen(
                locationId = locationId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}