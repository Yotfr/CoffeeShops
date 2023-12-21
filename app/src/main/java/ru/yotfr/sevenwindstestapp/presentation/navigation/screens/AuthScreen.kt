package ru.yotfr.sevenwindstestapp.presentation.navigation.screens

sealed class AuthScreen(val route: String) {
    data object Login : AuthScreen(route = "LOGIN")
    data object Register : AuthScreen(route = "REGISTER")
}
