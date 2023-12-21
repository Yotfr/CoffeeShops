package ru.yotfr.sevenwindstestapp.presentation.authorize.state

data class AuthorizeScreenState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isEmailEmptyError: Boolean = false,
    val isPasswordEmptyError: Boolean = false
)
