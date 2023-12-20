package ru.yotfr.sevenwindstestapp.presentation.authorize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.domain.common.onError
import ru.yotfr.sevenwindstestapp.domain.common.onLoading
import ru.yotfr.sevenwindstestapp.domain.common.onSuccess
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.usecase.AuthorizeUserUseCase
import ru.yotfr.sevenwindstestapp.presentation.authorize.event.AuthorizeEvent
import ru.yotfr.sevenwindstestapp.presentation.authorize.state.AuthorizeScreenState
import javax.inject.Inject

@HiltViewModel
class AuthorizeViewModel @Inject constructor(
    private val authorizeUserUseCase: AuthorizeUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthorizeScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthorizeEvent) {
        when(event) {
            is AuthorizeEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.newEmail
                    )
                }
            }
            is AuthorizeEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.newPassword
                    )
                }
            }
            AuthorizeEvent.AuthorizeClicked -> {
                with(_state.value) {
                    // TODO: Validate
                    viewModelScope.launch {
                        authorizeUserUseCase(
                            authModel = AuthModel(
                                login = email,
                                password = password
                            )
                        ).collectLatest { authorizeState ->
                            authorizeState.onLoading {
                                _state.update {
                                    it.copy(
                                        isLoading = true
                                    )
                                }
                            }.onError {
                                //TODO: Do Error state
                            }.onSuccess {
                                //TODO: Do success state
                            }
                        }
                    }
                }
            }
        }
    }
}