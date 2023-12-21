package ru.yotfr.sevenwindstestapp.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.yotfr.sevenwindstestapp.presentation.authorize.screen.AuthorizeScreen
import ru.yotfr.sevenwindstestapp.presentation.navigation.screens.AuthScreen
import ru.yotfr.sevenwindstestapp.presentation.register.screen.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Register.route
    ) {
        composable(
            route = AuthScreen.Register.route
        ) {
            RegisterScreen(
                navigateAuthorizeScreen = {
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(
            route = AuthScreen.Login.route
        ) {
            AuthorizeScreen(
                navigateRegisterScreen = {
                    navController.popBackStack()
                },
                navigateMainGraph = {
                    navController.navigate(
                        Graph.MAIN
                    ) {
                        popUpTo(Graph.MAIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}