package ru.yotfr.sevenwindstestapp.presentation.locations.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeAppBar
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeButton
import ru.yotfr.sevenwindstestapp.presentation.locations.viewmodel.LocationsViewModel
import ru.yotfr.sevenwindstestapp.presentation.utils.distance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    vm: LocationsViewModel = hiltViewModel(),
    navigateMapScreen: () -> Unit,
    navigateToLocationMenuScreen: (locationId: Int) -> Unit
) {

    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CoffeeAppBar(
                title = stringResource(id = R.string.nearest_shops),
                isTopLevel = true,
                onNavigationItemClick = {}
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
                item { Spacer(modifier = Modifier.height(15.dp)) }
                items(state.locations) { location ->
                    LocationItem(
                        name = location.name,
                        distance = location.distanceFromYou?.distance() ?: "",
                        onClick = { navigateToLocationMenuScreen(location.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(70.dp)) }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                CoffeeButton(
                    onClick = {
                        navigateMapScreen()
                    },
                    text = stringResource(id = R.string.on_map),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

        }
    }
}