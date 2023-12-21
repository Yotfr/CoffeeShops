package ru.yotfr.sevenwindstestapp.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CoffeeColors(
    val background: Color,
    val surface: Color,
    val lightPrimary: Color,
    val darkPrimary: Color,
    val secondary: Color,
    val inactive: Color,
    val onPrimary: Color
)

val LocalColors = staticCompositionLocalOf {
    CoffeeColors(
        background = Color.Unspecified,
        surface = Color.Unspecified,
        lightPrimary = Color.Unspecified,
        darkPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        inactive = Color.Unspecified,
        onPrimary = Color.Unspecified
    )
}

val backgroundColor = Color(0xFFFFFFFF)
val surfaceColor = Color(0xFFFAF9F9)
val brownDarkPrimary = Color(0xFF342D1A)
val brownLightPrimary = Color(0xFF846340)
val pinkSecondary = Color(0xFFF6E5D1)
val inactiveColor = Color(0xFFAF9479)
val onPrimaryColor = Color(0xFFF6E5D1)

