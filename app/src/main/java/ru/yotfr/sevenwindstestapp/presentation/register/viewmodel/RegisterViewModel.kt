package ru.yotfr.sevenwindstestapp.presentation.register.viewmodel

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
import ru.yotfr.sevenwindstestapp.domain.usecase.RegisterUserUseCase
import ru.yotfr.sevenwindstestapp.presentation.register.event.RegisterEvent
import ru.yotfr.sevenwindstestapp.presentation.register.event.RegisterOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.register.state.RegisterScreenState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<RegisterOneTimeEvent>()
    val event = _event.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.newEmail,
                        isEmailEmptyError = false
                    )
                }
            }

            is RegisterEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.newPassword,
                        isPasswordsNotMatchError = false,
                        isPasswordEmptyError = false
                    )
                }
            }

            is RegisterEvent.RepeatPasswordChanged -> {
                _state.update {
                    it.copy(
                        repeatPassword = event.newRepeatPassword,
                        isPasswordsNotMatchError = false
                    )
                }
            }

            RegisterEvent.RegisterClicked -> {
                with(_state.value) {
                    if (email.isBlank()) {
                        _state.update {
                            it.copy(
                                isEmailEmptyError = true
                            )
                        }
                        return
                    }
                    if (password.isBlank() && repeatPassword.isBlank()) {
                        _state.update {
                            it.copy(
                                isPasswordEmptyError = true
                            )
                        }
                        return
                    }
                    if (password.isBlank() || repeatPassword.isBlank() ||
                        password != repeatPassword
                    ) {
                        _state.update {
                            it.copy(
                                isPasswordsNotMatchError = true
                            )
                        }
                        return
                    }
                    viewModelScope.launch {
                        registerUserUseCase(
                            authModel = AuthModel(
                                login = email,
                                password = password
                            )
                        ).collectLatest { registerState ->
                            registerState.onLoading {
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
                                    RegisterOneTimeEvent.ShowErrorSnackbar(
                                        message = message
                                    )
                                )
                            }.onSuccess {
                                _event.send(
                                    RegisterOneTimeEvent.SuccessfullyRegistered
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}