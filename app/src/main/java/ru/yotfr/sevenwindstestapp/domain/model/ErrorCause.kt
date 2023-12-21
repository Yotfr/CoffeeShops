package ru.yotfr.sevenwindstestapp.domain.model

sealed interface ErrorCause {
    data object Unauthorized : ErrorCause
    data object Unknown : ErrorCause
}