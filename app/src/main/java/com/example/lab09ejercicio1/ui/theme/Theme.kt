package com.example.lab09ejercicio1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StoreColors = lightColorScheme(
    primary = StoreBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3E8FA),
    onPrimaryContainer = Color(0xFF26365E),
    secondary = StoreBlue,
    secondaryContainer = Color(0xFFD8DFF2),
    onSecondaryContainer = Color(0xFF26365E),
    background = StoreBackground,
    onBackground = StoreInk,
    surface = Color.White,
    onSurface = StoreInk,
    surfaceVariant = Color(0xFFEEEFF5),
    onSurfaceVariant = Color(0xFF5C6271)
)

@Composable
fun Lab09Ejercicio1Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StoreColors,
        typography = Typography,
        content = content
    )
}
