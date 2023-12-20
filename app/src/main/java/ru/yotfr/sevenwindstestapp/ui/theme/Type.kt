package ru.yotfr.sevenwindstestapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.yotfr.sevenwindstestapp.R

val sfDisplay = FontFamily(
    fonts = listOf(
        Font(R.font.sf_display_regular, FontWeight(400)),
        Font(R.font.sf_display_bold, FontWeight(700)),
        Font(R.font.sf_display_medium, FontWeight(500)),
    )
)

@Immutable
data class CoffeeTypography(
    val labelBold: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 21.48.sp,
        fontWeight = FontWeight(700),
        fontFamily = sfDisplay
    ),
    val body: TextStyle = TextStyle(
        fontSize = 15.sp,
        lineHeight = 17.9.sp,
        fontWeight = FontWeight(400),
        fontFamily = sfDisplay
    ),
    val labelRegular: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 21.48.sp,
        fontWeight = FontWeight(400),
        fontFamily = sfDisplay
    ),
    val captionRegular: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.71.sp,
        fontWeight = FontWeight(400),
        fontFamily = sfDisplay
    ),
    val captionBold: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.71.sp,
        fontWeight = FontWeight(700),
        fontFamily = sfDisplay
    ),
    val title: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.64.sp,
        fontWeight = FontWeight(500),
        fontFamily = sfDisplay
    )
)

val LocalTypography = staticCompositionLocalOf { CoffeeTypography() }