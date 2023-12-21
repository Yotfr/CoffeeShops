package ru.yotfr.sevenwindstestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.yotfr.sevenwindstestapp.presentation.navigation.graphs.RootNavGraph
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)
        val isAuthorized = intent.getBooleanExtra(IS_AUTHORIZED_INTENT_EXTRA_KEY, false)

        setContent {
            val navController = rememberNavController()

            CoffeeTheme {
                RootNavGraph(
                    navController = navController,
                    isAuthorized = isAuthorized
                )
            }
        }
    }

    companion object {
        const val IS_AUTHORIZED_INTENT_EXTRA_KEY = "IS_AUTHORIZED_INTENT_EXTRA_KEY"
    }
}