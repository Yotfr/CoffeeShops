package ru.yotfr.sevenwindstestapp.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object CoffeeTheme {
    val colors: CoffeeColors
        @Composable
        get() = LocalColors.current
    val shape: Shape
        @Composable
        get() = LocalShapes.current
    val typography: CoffeeTypography
        @Composable
        get() = LocalTypography.current
}

@Composable
fun CoffeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = CoffeeColors(
        background = backgroundColor,
        surface = surfaceColor,
        lightPrimary = brownLightPrimary,
        darkPrimary = brownDarkPrimary,
        secondary = pinkSecondary,
        inactive = inactiveColor,
        onPrimary = onPrimaryColor
    )
    val shape = Shape()
    val typography = CoffeeTypography()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides shape,
        LocalTypography provides typography
    ) {
        MaterialTheme(
            content = content
        )
    }


}