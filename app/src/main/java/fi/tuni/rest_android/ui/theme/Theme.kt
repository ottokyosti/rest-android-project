package fi.tuni.rest_android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Defines colors to be used when dark mode is on
private val DarkColorPalette = darkColors(
    primary = Color(0xFFFF5722),
    background = Color.Black,
    surface = Color.White
)

// Defines colors to be used when light mode is on
private val LightColorPalette = lightColors(
    primary = Color(0xFFFF5722),
    background = Color.White,
    surface = Color.Black
)

/**
 * Composable function that sets up the theme for the Android application.
 *
 * @param darkTheme Determines if the dark theme should be used.
 * @param content The content of the application.
 */
@Composable
fun RestAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(),
                     content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

/**
 * Returns the appropriate container color based on the current theme.
 *
 * @return The container color.
 */
@Composable
fun containerColor() : Color {
    return if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.background
    } else {
        Color(0xFF1A1A1A)
    }
}