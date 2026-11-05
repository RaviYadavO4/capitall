package com.portfolio.capitall.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Forceful Custom Dark Matrix Setup
private val PremiumDarkColorScheme = darkColorScheme(
    primary = AccentPurple,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun CapitallTheme(
    content: @Composable () -> Unit
) {
    // We strictly bypass Light/Dynamic themes to preserve premium fintech aesthetics
    MaterialTheme(
        colorScheme = PremiumDarkColorScheme,
        typography = Typography,
        content = content
    )
}
