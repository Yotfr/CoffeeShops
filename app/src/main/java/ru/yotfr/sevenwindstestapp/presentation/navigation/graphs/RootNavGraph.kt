package ru.yotfr.sevenwindstestapp.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(
    navController: NavHostController,
    isAuthorized: Boolean
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if (isAuthorized) Graph.MAIN else Graph.AUTH
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}

object Graph {
    const val ROOT = "ROOT_GRAPH"
    const val AUTH = "AUTH_GRAPH"
    const val MAIN = "MAIN_GRAPH"
}