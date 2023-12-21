package ru.yotfr.sevenwindstestapp.presentation.map.viewmodel

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
import ru.yotfr.sevenwindstestapp.domain.common.onError
import ru.yotfr.sevenwindstestapp.domain.common.onLoading
import ru.yotfr.sevenwindstestapp.domain.common.onSuccess
import ru.yotfr.sevenwindstestapp.domain.usecase.GetAvailableLocationsUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.GetCurrentLocationUseCase
import ru.yotfr.sevenwindstestapp.presentation.map.event.MapEvent
import ru.yotfr.sevenwindstestapp.presentation.map.event.MapOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.map.state.MapScreenState
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAvailableLocationsUseCase: GetAvailableLocationsUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MapScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<MapOneTimeEvent>()
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
                }.onError { message ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _event.send(
                        MapOneTimeEvent.ShowErrorSnackbar(
                            message = message
                        )
                    )
                }
            }
        }
        viewModelScope.launch {
            getCurrentLocationUseCase().onSuccess { coordinatesModel ->
                _state.update {
                    it.copy(
                        currentCoordinates = coordinatesModel
                    )
                }
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.PointClicked -> {
                with(_state.value) {
                    locations.find {
                        it.coordinates.latitude.roundToInt() == event.point.latitude.roundToInt() &&
                                it.coordinates.longitude.roundToInt() == event.point.longitude.roundToInt()
                    }?.let { neededLocation ->
                        viewModelScope.launch {
                            _event.send(
                                MapOneTimeEvent.NavigateLocationMenu(
                                    locationId = neededLocation.id
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}