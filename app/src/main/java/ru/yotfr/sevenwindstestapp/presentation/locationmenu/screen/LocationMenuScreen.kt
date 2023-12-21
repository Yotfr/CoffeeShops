package ru.yotfr.sevenwindstestapp.presentation.locationmenu.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeAppBar
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeButton
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuEvent
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.viewmodel.LocationMenuViewModel
import ru.yotfr.sevenwindstestapp.presentation.utils.observeWithLifecycle
import ru.yotfr.sevenwindstestapp.presentation.utils.rub

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun LocationMenuScreen(
    vm: LocationMenuViewModel = hiltViewModel(),
    locationId: Int,
    navigateBack: () -> Unit,
    navigatePaymentScreen: (locationId: Int) -> Unit,
    logout: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val state by vm.state.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = {
            vm.onEvent(LocationMenuEvent.PullRefresh)
        }
    )
    vm.event.observeWithLifecycle { event ->
        when(event) {
            is LocationMenuOneTimeEvent.ShowErrorSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        event.message ?: ContextCompat.getString(
                            context,
                            R.string.something_went_wrong
                        )
                    )
                }
            }

            LocationMenuOneTimeEvent.Logout -> {
                logout()
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.onEvent(
            LocationMenuEvent.EnteredScreen(
                locationId = locationId
            )
        )
    }

    Scaffold(
        topBar = {
            CoffeeAppBar(
                title = stringResource(id = R.string.menu),
                isTopLevel = false,
                onNavigationItemClick = navigateBack
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    horizontal = 16.dp
                )
                .pullRefresh(pullRefreshState)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(130.dp),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Modifier.height(16.dp)
                }
                items(state.locationMenu) { locationMenuItem ->
                    MenuItem(
                        url = locationMenuItem.menuModel.imageUrl ?: "",
                        name = locationMenuItem.menuModel.name ?: "",
                        price = locationMenuItem.menuModel.price.rub(),
                        onDecrease = {
                            vm.onEvent(
                                LocationMenuEvent.DecreaseItemCartCount(
                                    item = locationMenuItem,
                                    locationId = locationId
                                )
                            )
                        },
                        onIncrease = {
                            vm.onEvent(
                                LocationMenuEvent.IncreaseItemCartCount(
                                    item = locationMenuItem,
                                    locationId = locationId
                                )
                            )
                        },
                        count = locationMenuItem.count.toString()
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Modifier.height(90.dp)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                CoffeeButton(
                    onClick = {
                        Log.d("CLICK","CLICKED")
                        navigatePaymentScreen(locationId)
                              },
                    text = stringResource(id = R.string.to_payment),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}