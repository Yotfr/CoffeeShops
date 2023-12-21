package ru.yotfr.sevenwindstestapp.presentation.locationmenu.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.event.LocationMenuEvent
import ru.yotfr.sevenwindstestapp.presentation.locationmenu.viewmodel.LocationMenuViewModel
import ru.yotfr.sevenwindstestapp.presentation.utils.rub
import ru.yotfr.sevenwindstestapp.ui.theme.CoffeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationMenuScreen(
    vm: LocationMenuViewModel = hiltViewModel(),
    locationId: Int,
    navigateBack: () -> Unit
) {

    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.onEvent(
            LocationMenuEvent.EnteredScreen(
                locationId = locationId
            )
        )
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.menu),
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.lightPrimary
                )
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = CoffeeTheme.colors.surface
            )
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 10.dp, end = 16.dp
                )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(state.locationMenu) { locationMenuItem ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CoffeeTheme.shape.smallRounded,
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = CoffeeTheme.colors.background
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        AsyncImage(
                            model = locationMenuItem.item.imageUrl, contentDescription = null
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 11.dp, bottom = 11.dp, top = 10.dp, end = 5.dp)
                        ) {
                            Text(
                                text = locationMenuItem.item.name,
                                style = CoffeeTheme.typography.body,
                                color = CoffeeTheme.colors.inactive
                            )
                            Spacer(modifier = Modifier.height(9.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = locationMenuItem.item.price.rub(),
                                    style = CoffeeTheme.typography.captionBold,
                                    color = CoffeeTheme.colors.lightPrimary
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(
                                        onClick = {
                                            vm.onEvent(
                                                LocationMenuEvent.DecreaseItemCartCount(
                                                    item = locationMenuItem
                                                )
                                            )
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_minus),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = locationMenuItem.count.toString(),
                                        style = CoffeeTheme.typography.captionRegular,
                                        color = CoffeeTheme.colors.lightPrimary,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = {
                                            vm.onEvent(
                                                LocationMenuEvent.IncreaseItemCartCount(
                                                    item = locationMenuItem
                                                )
                                            )
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_plus),
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 13.dp),
                onClick = {
                    //TODO: к оплате
                },
                shape = CoffeeTheme.shape.largeRounded,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeTheme.colors.darkPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.to_payment),
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.onPrimary
                )
            }
        }
    }
}