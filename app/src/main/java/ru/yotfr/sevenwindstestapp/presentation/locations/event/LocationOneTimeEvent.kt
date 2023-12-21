package ru.yotfr.sevenwindstestapp.presentation.locations.event


sealed interface LocationOneTimeEvent {
    data class ShowErrorSnackbar(val message: String?) : LocationOneTimeEvent
}