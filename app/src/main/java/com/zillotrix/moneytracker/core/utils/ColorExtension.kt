package com.zillotrix.moneytracker.core.utils


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt

fun String.toComposeColor(): Color {
    return try {
        Color(this.toColorInt())
    } catch (e: Exception) {
        Color.Gray
    }
}

fun Color.toHexCode(): String {
    val argb = this.toArgb()
    return String.format("#%06X", 0xFFFFFF and argb)
}