package com.santos.valdomiro.meusgastos.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorSchemeFin = lightColorScheme(
    primary = PrimaryLightFin,
    onPrimary = OnPrimaryLightFin,

    primaryContainer = PrimaryContainerLightFin,
    onPrimaryContainer = OnPrimaryContainerLightFin,

    secondary = SecondaryLightFin,
    onSecondary = OnSecondaryLightFin,

    secondaryContainer = SecondaryContainerLightFin,
    onSecondaryContainer = OnSecondaryContainerLightFin,

    tertiary = TertiaryLightFin,
    onTertiary = OnTertiaryLightFin,

    tertiaryContainer = TertiaryContainerLightFin,
    onTertiaryContainer = OnTertiaryContainerLightFin,

    background = BackgroundLightFin,
    onBackground = OnBackgroundLightFin,

    surface = SurfaceLightFin,
    onSurface = OnSurfaceLightFin,

    surfaceVariant = SurfaceVariantLightFin,
    onSurfaceVariant = OnSurfaceVariantLightFin,

    outline = OutlineLightFin,
    outlineVariant = OutlineVariantLightFin,

    error = ErrorLightFin,
    onError = OnErrorLightFin
)

private val DarkColorSchemeFin = darkColorScheme(
    primary = PrimaryDarkFin,
    onPrimary = OnPrimaryDarkFin,

    primaryContainer = PrimaryContainerDarkFin,
    onPrimaryContainer = OnPrimaryContainerDarkFin,

    secondary = SecondaryDarkFin,
    onSecondary = OnSecondaryDarkFin,

    secondaryContainer = SecondaryContainerDarkFin,
    onSecondaryContainer = OnSecondaryContainerDarkFin,

    tertiary = TertiaryDarkFin,
    onTertiary = OnTertiaryDarkFin,

    tertiaryContainer = TertiaryContainerDarkFin,
    onTertiaryContainer = OnTertiaryContainerDarkFin,

    background = BackgroundDarkFin,
    onBackground = OnBackgroundDarkFin,

    surface = SurfaceDarkFin,
    onSurface = OnSurfaceDarkFin,

    surfaceVariant = SurfaceVariantDarkFin,
    onSurfaceVariant = OnSurfaceVariantDarkFin,

    outline = OutlineDarkFin,
    outlineVariant = OutlineVariantDarkFin,

    error = ErrorDarkFin,
    onError = OnErrorDarkFin
)

@Composable
fun MeusGastosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorSchemeFin
        else -> LightColorSchemeFin
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Pode alterar para algo como AppTypography se você tiver um arquivo Type.kt próprio
        // shapes = Shapes, // Descomente esta linha caso você crie um arquivo Shape.kt no futuro
        content = content
    )
}