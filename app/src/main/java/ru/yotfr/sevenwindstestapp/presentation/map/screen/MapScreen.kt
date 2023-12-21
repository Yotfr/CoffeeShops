package ru.yotfr.sevenwindstestapp.presentation.map.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeAppBar
import ru.yotfr.sevenwindstestapp.presentation.map.event.MapEvent
import ru.yotfr.sevenwindstestapp.presentation.map.event.MapOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.map.viewmodel.MapViewModel
import ru.yotfr.sevenwindstestapp.presentation.utils.OnStartOnStopEffect
import ru.yotfr.sevenwindstestapp.presentation.utils.observeWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    vm: MapViewModel = hiltViewModel(),
    navigateToLocationMenuScreen: (locationId: Int) -> Unit,
    navigateBack: () ->  Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    var mapView by remember { mutableStateOf<MapView?>(null) }
    val mapLocationTapListener = remember {
        MapObjectTapListener { _, point ->
            vm.onEvent(
                MapEvent.PointClicked(
                    point = point
                )
            )
            true
        }
    }

    val state by vm.state.collectAsState()
    vm.event.observeWithLifecycle { event ->
        when(event) {
            is MapOneTimeEvent.ShowErrorSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        event.message ?: ContextCompat.getString(
                            context,
                            R.string.something_went_wrong
                        )
                    )
                }
            }

            is MapOneTimeEvent.NavigateLocationMenu -> {
                navigateToLocationMenuScreen(event.locationId)
            }
        }
    }

    OnStartOnStopEffect(
        onStart = { MapKitFactory.getInstance().onStart() },
        onStop = { MapKitFactory.getInstance().onStop() }
    )

    LaunchedEffect(Unit, mapView, state.locations) {
        mapView?.let { map ->
            state.locations.forEach { location ->
                val imageProvider = ImageProvider.fromResource(context, R.drawable.ic_coffee_point)
                val placeMark = map.map.mapObjects.addPlacemark().apply {
                    geometry = Point(location.coordinates.latitude, location.coordinates.longitude)
                    setText(location.name)
                    setTextStyle(
                        TextStyle(
                            14.sp.value,
                            ContextCompat.getColor(context, R.color.brown),
                            null,
                            TextStyle.Placement.BOTTOM,
                            0.sp.value,
                            true,
                            true
                        )
                    )
                    setIcon(imageProvider)
                }
                placeMark.addTapListener(mapLocationTapListener)
            }
        }
    }

    LaunchedEffect(mapView, state.currentCoordinates) {
        mapView?.let { map ->
            map.map.move(
                CameraPosition(
                    Point(state.currentCoordinates.latitude, state.currentCoordinates.longitude),
                    14.0f,
                    150.0f,
                    0f
                )
            )
        }
    }

    Scaffold(
        topBar = {
            CoffeeAppBar(
                title = stringResource(id = R.string.map),
                isTopLevel = false,
                onNavigationItemClick = navigateBack
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        AndroidView(
            factory = {
                val view = MapView(it)
                mapView = view
                view
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}