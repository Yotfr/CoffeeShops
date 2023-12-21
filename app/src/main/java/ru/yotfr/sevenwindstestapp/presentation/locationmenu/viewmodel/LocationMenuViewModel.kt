package ru.yotfr.sevenwindstestapp.presentation.locationmenu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.domain.model.onError
import ru.yotfr.sevenwindstestapp.domain.model.onLoading
import ru.yotfr.sevenwindstestapp.domain.model.onSuccess
import ru.yotfr.sevenwindstestapp.domain.model.CartItemModel
import ru.yotfr.sevenwindstestapp.domain.model.ErrorCause
import ru.yotfr.sevenwindstestapp.domain.usecase.AddItemToCartUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.GetCartForLocationUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.GetLocationMenuUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.LogoutUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.RemoveItemFromCartUseCase
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuEvent
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.state.LocationMenuScreenState
import javax.inject.Inject

@HiltViewModel
class LocationMenuViewModel @Inject constructor(
    private val getCartForLocationUseCase: GetCartForLocationUseCase,
    private val getLocationMenuUseCase: GetLocationMenuUseCase,
    private val addItemToCartUseCase: AddItemToCartUseCase,
    private val removeItemFromCartUseCase: RemoveItemFromCartUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LocationMenuScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<LocationMenuOneTimeEvent>()
    val event = _event.receiveAsFlow()

    private val refreshTrigger = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onEvent(event: LocationMenuEvent) {
        when (event) {
            is LocationMenuEvent.IncreaseItemCartCount -> {
                viewModelScope.launch {
                    addItemToCartUseCase(
                        locationId = event.locationId,
                        item = event.item
                    )
                }
            }

            is LocationMenuEvent.DecreaseItemCartCount -> {
                viewModelScope.launch {
                    removeItemFromCartUseCase(
                        locationId = event.locationId,
                        item = event.item
                    )
                }
            }

            is LocationMenuEvent.EnteredScreen -> {
                viewModelScope.launch {
                    refreshTrigger.flatMapLatest {
                        combine(
                            getLocationMenuUseCase(
                                locationId = event.locationId
                            ),
                            getCartForLocationUseCase(
                                locationId = event.locationId
                            )
                        ) { locationMenuState, cartItems ->
                            Pair(locationMenuState, cartItems)
                        }
                    }.collectLatest { (locationMenuState, cartItems) ->
                        locationMenuState
                            .onLoading {
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
                                if (cause == ErrorCause.Unauthorized) {
                                    logoutUseCase()

                                } else {
                                    _event.send(
                                        LocationMenuOneTimeEvent.ShowErrorSnackbar(
                                            message = message
                                        )
                                    )
                                }
                            }.onSuccess { locationMenuItems ->
                                _state.update { state ->
                                    state.copy(
                                        isLoading = false,
                                        locationMenu = locationMenuItems.map { locationModel ->
                                            cartItems.find { it.menuModel == locationModel }
                                                ?: CartItemModel(
                                                    menuModel = locationModel,
                                                    count = 0
                                                )
                                        }
                                    )
                                }
                            }
                    }
                }
            }

            LocationMenuEvent.PullRefresh -> {
                refreshTrigger.value = !refreshTrigger.value
            }
        }
    }
}