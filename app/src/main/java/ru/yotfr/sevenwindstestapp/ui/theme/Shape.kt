package ru.yotfr.sevenwindstestapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class Shape(
    val smallRounded: Shape = RoundedCornerShape(5.dp),
    val largeRounded: Shape = RoundedCornerShape(16.dp),
)

val LocalShapes = staticCompositionLocalOf { Shape() }