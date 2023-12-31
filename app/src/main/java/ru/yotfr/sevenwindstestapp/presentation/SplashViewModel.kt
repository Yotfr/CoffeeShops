package ru.yotfr.sevenwindstestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.usecase.IsTokenPresentUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isTokenPresentUseCase: IsTokenPresentUseCase
) : ViewModel() {

    private val _isAuthorized = MutableSharedFlow<Boolean>(1,0,BufferOverflow.DROP_OLDEST)
    val isAuthorized = _isAuthorized.asSharedFlow()
    init {
        viewModelScope.launch {
            isTokenPresentUseCase().onError { message, cause ->
                _isAuthorized.emit(false)
            }.onSuccess { auth ->
                _isAuthorized.emit(auth)
            }
        }
    }
}