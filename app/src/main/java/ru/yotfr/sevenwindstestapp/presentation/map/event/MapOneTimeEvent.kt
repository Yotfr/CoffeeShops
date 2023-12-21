package ru.yotfr.sevenwindstestapp.presentation.map.event


sealed interface MapOneTimeEvent {
    data class ShowErrorSnackbar(val message: String?) : MapOneTimeEvent
    data class NavigateLocationMenu(val locationId: Int) : MapOneTimeEvent
}