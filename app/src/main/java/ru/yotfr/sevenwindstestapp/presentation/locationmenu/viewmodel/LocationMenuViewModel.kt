package ru.yotfr.sevenwindstestapp.presentation.locationmenu.viewmodel

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
import ru.yotfr.sevenwindstestapp.domain.model.LocationMenuCartItemModel
import ru.yotfr.sevenwindstestapp.domain.usecase.GetCartForLocationUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.GetLocationMenuUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.UpdateItemCountInCartUseCase
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuEvent
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.state.LocationMenuScreenState
import javax.inject.Inject

@HiltViewModel
class LocationMenuViewModel @Inject constructor(
    private val getCartForLocationUseCase: GetCartForLocationUseCase,
    private val getLocationMenuUseCase: GetLocationMenuUseCase,
    private val updateItemCountInCartUseCase: UpdateItemCountInCartUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(LocationMenuScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: LocationMenuEvent) {
        when(event) {
            is LocationMenuEvent.IncreaseItemCartCount -> {
                viewModelScope.launch {
                    updateItemCountInCartUseCase(
                        item = event.item.item,
                        newCount = event.item.count + 1
                    )
                }
            }

            is LocationMenuEvent.DecreaseItemCartCount -> {
                viewModelScope.launch {
                    updateItemCountInCartUseCase(
                        item = event.item.item,
                        newCount = event.item.count - 1
                    )
                }
            }

            is LocationMenuEvent.EnteredScreen -> {
                viewModelScope.launch {
                    getLocationMenuUseCase(
                        locationId = event.locationId
                    ).collectLatest { locationMenuState ->
                        locationMenuState.onLoading {
                            _state.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }.onError {
                            // TODO: Error state
                        }.onSuccess { locationMenuItems ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    locationMenu = locationMenuItems.map { menuItem ->
                                        LocationMenuCartItemModel(
                                            item = menuItem,
                                            count = 0
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                viewModelScope.launch {
                    getCartForLocationUseCase(
                        locationId = event.locationId
                    ).collectLatest { cartItems ->
                        _state.update {
                            it.copy(
                                locationMenu = _state.value.locationMenu.map { currentItem ->
                                    cartItems.find { it == currentItem } ?: kotlin.run {
                                        currentItem
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}