package ru.yotfr.sevenwindstestapp.presentation.locations.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.locations.viewmodel.LocationsViewModel
import ru.yotfr.sevenwindstestapp.ui.theme.CoffeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    vm: LocationsViewModel = hiltViewModel()
) {

    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.nearest_shops),
                        style = CoffeeTheme.typography.labelBold,
                        color = CoffeeTheme.colors.lightPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CoffeeTheme.colors.surface
                )
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.locations) { location ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CoffeeTheme.shape.smallRounded,
                        color = CoffeeTheme.colors.secondary,
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 14.dp,
                                    bottom = 9.dp
                                )
                        ) {
                            Text(
                                text = location.name,
                                style = CoffeeTheme.typography.labelBold,
                                color = CoffeeTheme.colors.darkPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = location.name,
                                style = CoffeeTheme.typography.captionRegular,
                                color = CoffeeTheme.colors.inactive
                            )
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 13.dp),
                onClick = {
                    //TODO: к карте
                },
                shape = CoffeeTheme.shape.largeRounded,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeTheme.colors.darkPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.on_map),
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.onPrimary
                )
            }
        }
    }
}