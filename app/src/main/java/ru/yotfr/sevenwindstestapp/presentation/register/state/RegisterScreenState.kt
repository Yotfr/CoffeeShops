package ru.yotfr.sevenwindstestapp.presentation.register.state

data class RegisterScreenState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
