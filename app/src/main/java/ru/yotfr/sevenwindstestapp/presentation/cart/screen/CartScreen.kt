package ru.yotfr.sevenwindstestapp.presentation.cart.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.cart.event.CartEvent
import ru.yotfr.sevenwindstestapp.presentation.cart.viewmodel.CartViewModel
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeAppBar
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeButton
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme
import ru.yotfr.sevenwindstestapp.presentation.utils.rub

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    vm: CartViewModel = hiltViewModel(),
    locationId: Int,
    navigateBack: () -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.onEvent(
            CartEvent.EnteredScreen(
                locationId = locationId
            )
        )
    }

    Scaffold(
        topBar = {
            CoffeeAppBar(
                title = stringResource(id = R.string.your_order),
                isTopLevel = false,
                onNavigationItemClick = navigateBack
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 10.dp,
                    end = 16.dp
                )
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(9.dp))
                }
                items(state.cartItems) { cartItem ->
                    CartItem(
                        name = cartItem.menuModel.name ?: "",
                        price = cartItem.menuModel.price.rub(),
                        onDecrease = {
                            vm.onEvent(
                                CartEvent.DecreaseItemCartCount(
                                    item = cartItem, locationId = locationId
                                )
                            )
                        },
                        onIncrease = {
                            vm.onEvent(
                                CartEvent.IncreaseItemCartCount(
                                    item = cartItem, locationId = locationId
                                )
                            )
                        },
                        count = cartItem.count.toString()
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(130.dp))
                }
                item {
                    Text(
                        text = stringResource(id = R.string.thank_for_order),
                        style = CoffeeTheme.typography.title,
                        color = CoffeeTheme.colors.lightPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                CoffeeButton(
                    onClick = { },
                    text = stringResource(id = R.string.pay),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}