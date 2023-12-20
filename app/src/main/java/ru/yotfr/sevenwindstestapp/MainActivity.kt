package ru.yotfr.sevenwindstestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.yotfr.sevenwindstestapp.ui.theme.SevenWindsTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SevenWindsTestAppTheme {
            }
        }
    }
}