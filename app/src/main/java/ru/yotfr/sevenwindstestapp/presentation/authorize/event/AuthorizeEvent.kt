package ru.yotfr.sevenwindstestapp.presentation.authorize.event


sealed interface AuthorizeEvent {
    data class EmailChanged(val newEmail: String): AuthorizeEvent
    data class PasswordChanged(val newPassword: String): AuthorizeEvent
    data object AuthorizeClicked : AuthorizeEvent
}