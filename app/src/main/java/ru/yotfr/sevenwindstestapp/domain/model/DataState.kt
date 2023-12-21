package ru.yotfr.sevenwindstestapp.domain.model

import retrofit2.HttpException

sealed class DataState<out T> {
    class Success<out T>(val data: T) : DataState<T>()
    class Error<T>(val message: String? = null, val cause: ErrorCause) : DataState<T>()
    class Loading<T> : DataState<T>()
}

inline fun <T> DataState<T>.onSuccess(
    executable: (T) -> Unit
): DataState<T> = apply {
    if (this is DataState.Success<T>) {
        executable(data)
    }
}

inline fun <T> DataState<T>.onError(
    executable: (message: String?, cause: ErrorCause) -> Unit
): DataState<T> = apply {
    if (this is DataState.Error<T>) {
        executable(
            message,
            cause
        )
    }
}

inline fun <T> DataState<T>.onLoading(
    executable: () -> Unit
): DataState<T> = apply {
    if (this is DataState.Loading<T>) {
        executable()
    }
}

inline fun <T> errorCatchingValue(
    block: () -> T
): DataState<T> {
    return try {
        DataState.Success(
            data = block()
        )
    } catch (e: Exception) {
        if (e is HttpException) {
            if (e.code() == 401) {
                return DataState.Error(
                    message = e.message,
                    cause = ErrorCause.Unauthorized
                )
            } else {
                return DataState.Error(
                    message = e.message,
                    cause = ErrorCause.Unknown
                )
            }
        } else {
            return DataState.Error(
                message = e.message,
                cause = ErrorCause.Unknown
            )
        }
    }
}

inline fun <T> errorCatchingState(
    block: () -> DataState<T>
): DataState<T> {
    return try {
        block()
    } catch (e: Exception) {
        if (e is HttpException) {
            if (e.code() == 401) {
                return DataState.Error(
                    message = e.message,
                    cause = ErrorCause.Unauthorized
                )
            } else {
                return DataState.Error(
                    message = e.message,
                    cause = ErrorCause.Unknown
                )
            }
        } else {
            return DataState.Error(
                message = e.message,
                cause = ErrorCause.Unknown
            )
        }
    }
}

inline fun <T, R> DataState<T>.mapType(
    map: (T) -> R
): DataState<R> {
    return when (this) {
        is DataState.Error -> DataState.Error(message = message, cause = cause)
        is DataState.Loading -> DataState.Loading()
        is DataState.Success -> DataState.Success(data = map(data))
    }
}