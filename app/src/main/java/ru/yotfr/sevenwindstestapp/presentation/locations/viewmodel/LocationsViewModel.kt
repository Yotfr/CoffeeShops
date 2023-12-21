package ru.yotfr.sevenwindstestapp.presentation.locations.viewmodel

import androidx.compose.runtime.mutableStateListOf
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
import ru.yotfr.sevenwindstestapp.domain.usecase.GetAvailableLocationsUseCase
import ru.yotfr.sevenwindstestapp.presentation.locations.state.LocationsScreenState
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getAvailableLocationsUseCase: GetAvailableLocationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LocationsScreenState())
    val state = _state.asStateFlow()

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
                }.onError {
                    // TODO: Do error state
                }
            }
        }
    }
}