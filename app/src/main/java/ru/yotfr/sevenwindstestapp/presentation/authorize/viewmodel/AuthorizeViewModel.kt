package ru.yotfr.sevenwindstestapp.presentation.authorize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onLoading
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.model.AuthModel
import ru.yotfr.sevenwindstestapp.domain.usecase.AuthorizeUserUseCase
import ru.yotfr.sevenwindstestapp.presentation.authorize.event.AuthorizeEvent
import ru.yotfr.sevenwindstestapp.presentation.authorize.event.AuthorizeOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.authorize.state.AuthorizeScreenState
import javax.inject.Inject

@HiltViewModel
class AuthorizeViewModel @Inject constructor(
    private val authorizeUserUseCase: AuthorizeUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthorizeScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthorizeOneTimeEvent>()
    val event = _event.receiveAsFlow()

    fun onEvent(event: AuthorizeEvent) {
        when(event) {
            is AuthorizeEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.newEmail,
                        isEmailEmptyError = false
                    )
                }
            }
            is AuthorizeEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.newPassword,
                        isPasswordEmptyError = false
                    )
                }
            }
            AuthorizeEvent.AuthorizeClicked -> {
                with(_state.value) {
                    if (email.isBlank()) {
                        _state.update {
                            it.copy(
                                isEmailEmptyError = true
                            )
                        }
                        return
                    }
                    if (password.isBlank()) {
                        _state.update {
                            it.copy(
                                isEmailEmptyError = true
                            )
                        }
                        return
                    }
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
                            }.onError { message, cause ->
                                _state.update {
                                    it.copy(
                                        isLoading = false
                                    )
                                }
                                _event.send(
                                    AuthorizeOneTimeEvent.ShowErrorSnackbar(
                                        message = message
                                    )
                                )
                            }.onSuccess {
                                _event.send(
                                    AuthorizeOneTimeEvent.SuccessfullyAuthorized
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}