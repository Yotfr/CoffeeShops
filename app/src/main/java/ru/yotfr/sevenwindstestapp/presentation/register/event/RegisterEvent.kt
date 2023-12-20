package ru.yotfr.sevenwindstestapp.presentation.register.event

sealed interface RegisterEvent {
    data class EmailChanged(val newEmail: String): RegisterEvent
    data class PasswordChanged(val newPassword: String): RegisterEvent
    data class RepeatPasswordChanged(val newRepeatPassword: String): RegisterEvent
    data object RegisterClicked : RegisterEvent
}