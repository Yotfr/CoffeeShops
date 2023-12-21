package ru.yotfr.sevenwindstestapp.presentation.authorize.event

sealed interface AuthorizeOneTimeEvent {
    data class ShowErrorSnackbar(val message: String?) : AuthorizeOneTimeEvent
    data object SuccessfullyAuthorized : AuthorizeOneTimeEvent
}