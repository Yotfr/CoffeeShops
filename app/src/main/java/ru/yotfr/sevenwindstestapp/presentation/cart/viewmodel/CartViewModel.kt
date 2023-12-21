package ru.yotfr.sevenwindstestapp.presentation.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.domain.usecase.GetCartForLocationUseCase
import ru.yotfr.sevenwindstestapp.domain.usecase.UpdateItemCountInCartUseCase
import ru.yotfr.sevenwindstestapp.presentation.cart.event.CartEvent
import ru.yotfr.sevenwindstestapp.presentation.cart.state.CartScreenState
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val updateItemCountInCartUseCase: UpdateItemCountInCartUseCase,
    private val getCartForLocationUseCase: GetCartForLocationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.EnteredScreen -> {
                viewModelScope.launch {
                    getCartForLocationUseCase(
                        locationId = event.locationId
                    ).collectLatest { cartItems ->
                        _state.update {
                            it.copy(
                                cartItems = cartItems
                            )
                        }
                    }
                }
            }
            is CartEvent.DecreaseItemCartCount -> {
                viewModelScope.launch {
                    updateItemCountInCartUseCase(
                        item = event.item.item,
                        newCount = event.item.count - 1
                    )
                }
            }
            is CartEvent.IncreaseItemCartCount -> {
                viewModelScope.launch {
                    updateItemCountInCartUseCase(
                        item = event.item.item,
                        newCount = event.item.count + 1
                    )
                }
            }
        }
    }
}