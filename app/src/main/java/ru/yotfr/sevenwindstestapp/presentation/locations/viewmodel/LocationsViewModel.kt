package ru.yotfr.sevenwindstestapp.presentation.locations.viewmodel

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
import ru.yotfr.sevenwindstestapp.domain.model.ErrorCause
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onLoading
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.usecase.GetAvailableLocationsUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.LogoutUseCase
import ru.yotfr.sevenwindstestapp.presentation.locations.event.LocationOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.locations.state.LocationsScreenState
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getAvailableLocationsUseCase: GetAvailableLocationsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LocationsScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<LocationOneTimeEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            getAvailableLocationsUseCase().collectLatest { getLocationsState ->
                getLocationsState.onLoading {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }.onSuccess { locations ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            locations = locations
                        )
                    }
                }.onError { message, cause ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    if (cause == ErrorCause.Unauthorized) {
                        _event.send(
                            LocationOneTimeEvent.Logout
                        )
                    } else {
                        _event.send(
                            LocationOneTimeEvent.ShowErrorSnackbar(
                                message = message
                            )
                        )
                    }
                }
            }
        }
    }
}