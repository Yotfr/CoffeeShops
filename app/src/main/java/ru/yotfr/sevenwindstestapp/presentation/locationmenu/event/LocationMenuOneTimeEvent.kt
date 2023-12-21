package ru.yotfr.sevenwindstestapp.presentation.locationmenu.event

sealed interface LocationMenuOneTimeEvent {
    data class ShowErrorSnackbar(val message: String?) : LocationMenuOneTimeEvent
    data object Logout : LocationMenuOneTimeEvent
}