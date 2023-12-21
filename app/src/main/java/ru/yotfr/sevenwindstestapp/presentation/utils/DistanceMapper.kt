package ru.yotfr.sevenwindstestapp.presentation.utils

import kotlin.math.roundToInt

fun Double.distance(): String {
    return "${this.roundToInt()} км от вас"
}