package ru.yotfr.sevenwindstestapp.domain.common

sealed class DataState<out T> {
    class Success<out T>(val data: T) : DataState<T>()
    class Error<T>(val message: String? = null) : DataState<T>()
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
    executable: (message: String?) -> Unit
): DataState<T> = apply {
    if (this is DataState.Error<T>) {
        executable(
            message
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
        return DataState.Error(
            message = e.message
        )
    }
}

inline fun <T> errorCatchingState(
    block: () -> DataState<T>
): DataState<T> {
    return try {
        block()
    } catch (e: Exception) {
        return DataState.Error(
            message = e.message
        )
    }
}

inline fun <T, R> DataState<T>.mapType(
    map: (T) -> R
): DataState<R> {
    return when (this) {
        is DataState.Error -> DataState.Error(message = message)
        is DataState.Loading -> DataState.Loading()
        is DataState.Success -> DataState.Success(data = map(data))
    }
}