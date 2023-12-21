package ru.yotfr.sevenwindstestapp.presentation.register.event

sealed interface RegisterOneTimeEvent {
    data class ShowErrorSnackbar(val message: String?) : RegisterOneTimeEvent
    data object SuccessfullyRegistered : RegisterOneTimeEvent
}