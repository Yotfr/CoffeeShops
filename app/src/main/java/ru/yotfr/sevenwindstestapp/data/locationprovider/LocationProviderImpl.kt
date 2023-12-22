package ru.yotfr.sevenwindstestapp.data.locationprovider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.locationprovider.LocationProvider
import ru.yotfr.sevenwindstestapp.domain.model.CoordinatesModel
import ru.yotfr.sevenwindstestapp.domain.model.ErrorCause
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationProviderImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val appContext: Context
) : LocationProvider {

    override suspend fun getCurrentCoordinates(): DataState<CoordinatesModel> {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationManager =
            appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission || !isGpsEnabled) {
            return DataState.Error(
                null,
                ErrorCause.Unknown
            )
        }
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(
                            DataState.Success(
                                data = CoordinatesModel(
                                    latitude = result.latitude,
                                    longitude = result.longitude
                                )
                            )
                        )
                    } else {
                        continuation.resume(
                            DataState.Error(
                                null,
                                ErrorCause.Unknown
                            )
                        )
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { location ->
                    continuation.resume(
                        DataState.Success(
                            data = CoordinatesModel(
                                latitude = result.latitude,
                                longitude = result.longitude
                            )
                        )
                    )
                }
                addOnFailureListener {
                    continuation.resume(
                        DataState.Error(
                            null,
                            ErrorCause.Unknown
                        )
                    )
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}