package ru.yotfr.sevenwindstestapp.presentation.register.viewmodel

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
import ru.yotfr.sevenwindstestapp.domain.usecase.RegisterUserUseCase
import ru.yotfr.sevenwindstestapp.presentation.register.event.RegisterEvent
import ru.yotfr.sevenwindstestapp.presentation.register.state.RegisterScreenState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.newEmail
                    )
                }
            }
            is RegisterEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.newPassword
                    )
                }
            }
            is RegisterEvent.RepeatPasswordChanged -> {
                _state.update {
                    it.copy(
                        repeatPassword = event.newRepeatPassword
                    )
                }
            }
            RegisterEvent.RegisterClicked -> {
                with(_state.value) {
                    // TODO: Validate
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