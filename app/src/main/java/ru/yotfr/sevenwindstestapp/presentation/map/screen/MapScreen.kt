package ru.yotfr.sevenwindstestapp.presentation.map.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.map.viewmodel.MapViewModel
import ru.yotfr.sevenwindstestapp.presentation.utils.OnStartOnStopEffect

@Preview
@Composable
fun MapScreenTest() {
    MapScreen()
}

@Composable
fun MapScreen(
    vm: MapViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    val state by vm.state.collectAsState()
    val mapLocationTapListener = remember {
        MapObjectTapListener { _, point ->
            //TODO: do clicks
            true
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = {
                val view = MapView(it)
                mapView = view
                view
            }
        )

    }
}