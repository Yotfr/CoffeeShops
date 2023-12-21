package ru.yotfr.sevenwindstestapp.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.yotfr.sevenwindstestapp.presentation.common.LocationPermissionTextProvider
import ru.yotfr.sevenwindstestapp.presentation.common.PermissionDialog
import ru.yotfr.sevenwindstestapp.presentation.navigation.graphs.RootNavGraph
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)
        val isAuthorized = intent.getBooleanExtra(IS_AUTHORIZED_INTENT_EXTRA_KEY, false)

        setContent {
            val navController = rememberNavController()
            CoffeeTheme {
                val permissionsToRequest = remember {
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                }
                val dialogQueue = vm.visiblePermissionDialogQueue
                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { permissions ->
                        permissionsToRequest.forEach { permission ->
                            vm.onPermissionResult(
                                permission = permission,
                                isGranted = permissions[permission] == true
                            )
                        }
                    }
                )

                LaunchedEffect(Unit) {
                    multiplePermissionResultLauncher.launch(permissionsToRequest)
                }

                RootNavGraph(
                    navController = navController,
                    isAuthorized = isAuthorized
                )

                dialogQueue
                    .reversed()
                    .forEach { permission ->
                        PermissionDialog(
                            permissionTextProvider = when (permission) {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                    LocationPermissionTextProvider()
                                }

                                else -> return@forEach
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                permission
                            ),
                            onDismiss = { vm::dismissDialog },
                            onOkClick = {
                                vm.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
                    }
            }
        }
    }

    companion object {
        const val IS_AUTHORIZED_INTENT_EXTRA_KEY = "IS_AUTHORIZED_INTENT_EXTRA_KEY"
    }
}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}