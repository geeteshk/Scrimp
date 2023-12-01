package com.glew.scrimp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import com.glew.scrimp.data.ExpenseCategory

private val DarkColorScheme = darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40

        /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/*private val AccommodationLightColors = lightColorScheme(
    primary = accommodation_light_primary,
    onPrimary = accommodation_light_onPrimary,
    primaryContainer = accommodation_light_primaryContainer,
    onPrimaryContainer = accommodation_light_onPrimaryContainer,
    secondary = accommodation_light_secondary,
    onSecondary = accommodation_light_onSecondary,
    secondaryContainer = accommodation_light_secondaryContainer,
    onSecondaryContainer = accommodation_light_onSecondaryContainer,
    tertiary = accommodation_light_tertiary,
    onTertiary = accommodation_light_onTertiary,
    tertiaryContainer = accommodation_light_tertiaryContainer,
    onTertiaryContainer = accommodation_light_onTertiaryContainer,
    error = accommodation_light_error,
    errorContainer = accommodation_light_errorContainer,
    onError = accommodation_light_onError,
    onErrorContainer = accommodation_light_onErrorContainer,
    background = accommodation_light_background,
    onBackground = accommodation_light_onBackground,
    surface = accommodation_light_surface,
    onSurface = accommodation_light_onSurface,
    surfaceVariant = accommodation_light_surfaceVariant,
    onSurfaceVariant = accommodation_light_onSurfaceVariant,
    outline = accommodation_light_outline,
    inverseOnSurface = accommodation_light_inverseOnSurface,
    inverseSurface = accommodation_light_inverseSurface,
    inversePrimary = accommodation_light_inversePrimary,
    surfaceTint = accommodation_light_surfaceTint,
    outlineVariant = accommodation_light_outlineVariant,
    scrim = accommodation_light_scrim,
)


private val AccommodationDarkColors = darkColorScheme(
    primary = accommodation_dark_primary,
    onPrimary = accommodation_dark_onPrimary,
    primaryContainer = accommodation_dark_primaryContainer,
    onPrimaryContainer = accommodation_dark_onPrimaryContainer,
    secondary = accommodation_dark_secondary,
    onSecondary = accommodation_dark_onSecondary,
    secondaryContainer = accommodation_dark_secondaryContainer,
    onSecondaryContainer = accommodation_dark_onSecondaryContainer,
    tertiary = accommodation_dark_tertiary,
    onTertiary = accommodation_dark_onTertiary,
    tertiaryContainer = accommodation_dark_tertiaryContainer,
    onTertiaryContainer = accommodation_dark_onTertiaryContainer,
    error = accommodation_dark_error,
    errorContainer = accommodation_dark_errorContainer,
    onError = accommodation_dark_onError,
    onErrorContainer = accommodation_dark_onErrorContainer,
    background = accommodation_dark_background,
    onBackground = accommodation_dark_onBackground,
    surface = accommodation_dark_surface,
    onSurface = accommodation_dark_onSurface,
    surfaceVariant = accommodation_dark_surfaceVariant,
    onSurfaceVariant = accommodation_dark_onSurfaceVariant,
    outline = accommodation_dark_outline,
    inverseOnSurface = accommodation_dark_inverseOnSurface,
    inverseSurface = accommodation_dark_inverseSurface,
    inversePrimary = accommodation_dark_inversePrimary,
    surfaceTint = accommodation_dark_surfaceTint,
    outlineVariant = accommodation_dark_outlineVariant,
    scrim = accommodation_dark_scrim,
)

private val TransportationLightColors = lightColorScheme(
    primary = transportation_light_primary,
    onPrimary = transportation_light_onPrimary,
    primaryContainer = transportation_light_primaryContainer,
    onPrimaryContainer = transportation_light_onPrimaryContainer,
    secondary = transportation_light_secondary,
    onSecondary = transportation_light_onSecondary,
    secondaryContainer = transportation_light_secondaryContainer,
    onSecondaryContainer = transportation_light_onSecondaryContainer,
    tertiary = transportation_light_tertiary,
    onTertiary = transportation_light_onTertiary,
    tertiaryContainer = transportation_light_tertiaryContainer,
    onTertiaryContainer = transportation_light_onTertiaryContainer,
    error = transportation_light_error,
    errorContainer = transportation_light_errorContainer,
    onError = transportation_light_onError,
    onErrorContainer = transportation_light_onErrorContainer,
    background = transportation_light_background,
    onBackground = transportation_light_onBackground,
    surface = transportation_light_surface,
    onSurface = transportation_light_onSurface,
    surfaceVariant = transportation_light_surfaceVariant,
    onSurfaceVariant = transportation_light_onSurfaceVariant,
    outline = transportation_light_outline,
    inverseOnSurface = transportation_light_inverseOnSurface,
    inverseSurface = transportation_light_inverseSurface,
    inversePrimary = transportation_light_inversePrimary,
    surfaceTint = transportation_light_surfaceTint,
    outlineVariant = transportation_light_outlineVariant,
    scrim = transportation_light_scrim,
)


private val TransportationDarkColors = darkColorScheme(
    primary = transportation_dark_primary,
    onPrimary = transportation_dark_onPrimary,
    primaryContainer = transportation_dark_primaryContainer,
    onPrimaryContainer = transportation_dark_onPrimaryContainer,
    secondary = transportation_dark_secondary,
    onSecondary = transportation_dark_onSecondary,
    secondaryContainer = transportation_dark_secondaryContainer,
    onSecondaryContainer = transportation_dark_onSecondaryContainer,
    tertiary = transportation_dark_tertiary,
    onTertiary = transportation_dark_onTertiary,
    tertiaryContainer = transportation_dark_tertiaryContainer,
    onTertiaryContainer = transportation_dark_onTertiaryContainer,
    error = transportation_dark_error,
    errorContainer = transportation_dark_errorContainer,
    onError = transportation_dark_onError,
    onErrorContainer = transportation_dark_onErrorContainer,
    background = transportation_dark_background,
    onBackground = transportation_dark_onBackground,
    surface = transportation_dark_surface,
    onSurface = transportation_dark_onSurface,
    surfaceVariant = transportation_dark_surfaceVariant,
    onSurfaceVariant = transportation_dark_onSurfaceVariant,
    outline = transportation_dark_outline,
    inverseOnSurface = transportation_dark_inverseOnSurface,
    inverseSurface = transportation_dark_inverseSurface,
    inversePrimary = transportation_dark_inversePrimary,
    surfaceTint = transportation_dark_surfaceTint,
    outlineVariant = transportation_dark_outlineVariant,
    scrim = transportation_dark_scrim,
)

private val FoodLightColors = lightColorScheme(
    primary = food_light_primary,
    onPrimary = food_light_onPrimary,
    primaryContainer = food_light_primaryContainer,
    onPrimaryContainer = food_light_onPrimaryContainer,
    secondary = food_light_secondary,
    onSecondary = food_light_onSecondary,
    secondaryContainer = food_light_secondaryContainer,
    onSecondaryContainer = food_light_onSecondaryContainer,
    tertiary = food_light_tertiary,
    onTertiary = food_light_onTertiary,
    tertiaryContainer = food_light_tertiaryContainer,
    onTertiaryContainer = food_light_onTertiaryContainer,
    error = food_light_error,
    errorContainer = food_light_errorContainer,
    onError = food_light_onError,
    onErrorContainer = food_light_onErrorContainer,
    background = food_light_background,
    onBackground = food_light_onBackground,
    surface = food_light_surface,
    onSurface = food_light_onSurface,
    surfaceVariant = food_light_surfaceVariant,
    onSurfaceVariant = food_light_onSurfaceVariant,
    outline = food_light_outline,
    inverseOnSurface = food_light_inverseOnSurface,
    inverseSurface = food_light_inverseSurface,
    inversePrimary = food_light_inversePrimary,
    surfaceTint = food_light_surfaceTint,
    outlineVariant = food_light_outlineVariant,
    scrim = food_light_scrim,
)


private val FoodDarkColors = darkColorScheme(
    primary = food_dark_primary,
    onPrimary = food_dark_onPrimary,
    primaryContainer = food_dark_primaryContainer,
    onPrimaryContainer = food_dark_onPrimaryContainer,
    secondary = food_dark_secondary,
    onSecondary = food_dark_onSecondary,
    secondaryContainer = food_dark_secondaryContainer,
    onSecondaryContainer = food_dark_onSecondaryContainer,
    tertiary = food_dark_tertiary,
    onTertiary = food_dark_onTertiary,
    tertiaryContainer = food_dark_tertiaryContainer,
    onTertiaryContainer = food_dark_onTertiaryContainer,
    error = food_dark_error,
    errorContainer = food_dark_errorContainer,
    onError = food_dark_onError,
    onErrorContainer = food_dark_onErrorContainer,
    background = food_dark_background,
    onBackground = food_dark_onBackground,
    surface = food_dark_surface,
    onSurface = food_dark_onSurface,
    surfaceVariant = food_dark_surfaceVariant,
    onSurfaceVariant = food_dark_onSurfaceVariant,
    outline = food_dark_outline,
    inverseOnSurface = food_dark_inverseOnSurface,
    inverseSurface = food_dark_inverseSurface,
    inversePrimary = food_dark_inversePrimary,
    surfaceTint = food_dark_surfaceTint,
    outlineVariant = food_dark_outlineVariant,
    scrim = food_dark_scrim,
)

private val EntertainmentLightColors = lightColorScheme(
    primary = entertainment_light_primary,
    onPrimary = entertainment_light_onPrimary,
    primaryContainer = entertainment_light_primaryContainer,
    onPrimaryContainer = entertainment_light_onPrimaryContainer,
    secondary = entertainment_light_secondary,
    onSecondary = entertainment_light_onSecondary,
    secondaryContainer = entertainment_light_secondaryContainer,
    onSecondaryContainer = entertainment_light_onSecondaryContainer,
    tertiary = entertainment_light_tertiary,
    onTertiary = entertainment_light_onTertiary,
    tertiaryContainer = entertainment_light_tertiaryContainer,
    onTertiaryContainer = entertainment_light_onTertiaryContainer,
    error = entertainment_light_error,
    errorContainer = entertainment_light_errorContainer,
    onError = entertainment_light_onError,
    onErrorContainer = entertainment_light_onErrorContainer,
    background = entertainment_light_background,
    onBackground = entertainment_light_onBackground,
    surface = entertainment_light_surface,
    onSurface = entertainment_light_onSurface,
    surfaceVariant = entertainment_light_surfaceVariant,
    onSurfaceVariant = entertainment_light_onSurfaceVariant,
    outline = entertainment_light_outline,
    inverseOnSurface = entertainment_light_inverseOnSurface,
    inverseSurface = entertainment_light_inverseSurface,
    inversePrimary = entertainment_light_inversePrimary,
    surfaceTint = entertainment_light_surfaceTint,
    outlineVariant = entertainment_light_outlineVariant,
    scrim = entertainment_light_scrim,
)


private val EntertainmentDarkColors = darkColorScheme(
    primary = entertainment_dark_primary,
    onPrimary = entertainment_dark_onPrimary,
    primaryContainer = entertainment_dark_primaryContainer,
    onPrimaryContainer = entertainment_dark_onPrimaryContainer,
    secondary = entertainment_dark_secondary,
    onSecondary = entertainment_dark_onSecondary,
    secondaryContainer = entertainment_dark_secondaryContainer,
    onSecondaryContainer = entertainment_dark_onSecondaryContainer,
    tertiary = entertainment_dark_tertiary,
    onTertiary = entertainment_dark_onTertiary,
    tertiaryContainer = entertainment_dark_tertiaryContainer,
    onTertiaryContainer = entertainment_dark_onTertiaryContainer,
    error = entertainment_dark_error,
    errorContainer = entertainment_dark_errorContainer,
    onError = entertainment_dark_onError,
    onErrorContainer = entertainment_dark_onErrorContainer,
    background = entertainment_dark_background,
    onBackground = entertainment_dark_onBackground,
    surface = entertainment_dark_surface,
    onSurface = entertainment_dark_onSurface,
    surfaceVariant = entertainment_dark_surfaceVariant,
    onSurfaceVariant = entertainment_dark_onSurfaceVariant,
    outline = entertainment_dark_outline,
    inverseOnSurface = entertainment_dark_inverseOnSurface,
    inverseSurface = entertainment_dark_inverseSurface,
    inversePrimary = entertainment_dark_inversePrimary,
    surfaceTint = entertainment_dark_surfaceTint,
    outlineVariant = entertainment_dark_outlineVariant,
    scrim = entertainment_dark_scrim,
)

private val ShoppingLightColors = lightColorScheme(
    primary = shopping_light_primary,
    onPrimary = shopping_light_onPrimary,
    primaryContainer = shopping_light_primaryContainer,
    onPrimaryContainer = shopping_light_onPrimaryContainer,
    secondary = shopping_light_secondary,
    onSecondary = shopping_light_onSecondary,
    secondaryContainer = shopping_light_secondaryContainer,
    onSecondaryContainer = shopping_light_onSecondaryContainer,
    tertiary = shopping_light_tertiary,
    onTertiary = shopping_light_onTertiary,
    tertiaryContainer = shopping_light_tertiaryContainer,
    onTertiaryContainer = shopping_light_onTertiaryContainer,
    error = shopping_light_error,
    errorContainer = shopping_light_errorContainer,
    onError = shopping_light_onError,
    onErrorContainer = shopping_light_onErrorContainer,
    background = shopping_light_background,
    onBackground = shopping_light_onBackground,
    surface = shopping_light_surface,
    onSurface = shopping_light_onSurface,
    surfaceVariant = shopping_light_surfaceVariant,
    onSurfaceVariant = shopping_light_onSurfaceVariant,
    outline = shopping_light_outline,
    inverseOnSurface = shopping_light_inverseOnSurface,
    inverseSurface = shopping_light_inverseSurface,
    inversePrimary = shopping_light_inversePrimary,
    surfaceTint = shopping_light_surfaceTint,
    outlineVariant = shopping_light_outlineVariant,
    scrim = shopping_light_scrim,
)


private val ShoppingDarkColors = darkColorScheme(
    primary = shopping_dark_primary,
    onPrimary = shopping_dark_onPrimary,
    primaryContainer = shopping_dark_primaryContainer,
    onPrimaryContainer = shopping_dark_onPrimaryContainer,
    secondary = shopping_dark_secondary,
    onSecondary = shopping_dark_onSecondary,
    secondaryContainer = shopping_dark_secondaryContainer,
    onSecondaryContainer = shopping_dark_onSecondaryContainer,
    tertiary = shopping_dark_tertiary,
    onTertiary = shopping_dark_onTertiary,
    tertiaryContainer = shopping_dark_tertiaryContainer,
    onTertiaryContainer = shopping_dark_onTertiaryContainer,
    error = shopping_dark_error,
    errorContainer = shopping_dark_errorContainer,
    onError = shopping_dark_onError,
    onErrorContainer = shopping_dark_onErrorContainer,
    background = shopping_dark_background,
    onBackground = shopping_dark_onBackground,
    surface = shopping_dark_surface,
    onSurface = shopping_dark_onSurface,
    surfaceVariant = shopping_dark_surfaceVariant,
    onSurfaceVariant = shopping_dark_onSurfaceVariant,
    outline = shopping_dark_outline,
    inverseOnSurface = shopping_dark_inverseOnSurface,
    inverseSurface = shopping_dark_inverseSurface,
    inversePrimary = shopping_dark_inversePrimary,
    surfaceTint = shopping_dark_surfaceTint,
    outlineVariant = shopping_dark_outlineVariant,
    scrim = shopping_dark_scrim,
)

private val OtherLightColors = lightColorScheme(
    primary = other_light_primary,
    onPrimary = other_light_onPrimary,
    primaryContainer = other_light_primaryContainer,
    onPrimaryContainer = other_light_onPrimaryContainer,
    secondary = other_light_secondary,
    onSecondary = other_light_onSecondary,
    secondaryContainer = other_light_secondaryContainer,
    onSecondaryContainer = other_light_onSecondaryContainer,
    tertiary = other_light_tertiary,
    onTertiary = other_light_onTertiary,
    tertiaryContainer = other_light_tertiaryContainer,
    onTertiaryContainer = other_light_onTertiaryContainer,
    error = other_light_error,
    errorContainer = other_light_errorContainer,
    onError = other_light_onError,
    onErrorContainer = other_light_onErrorContainer,
    background = other_light_background,
    onBackground = other_light_onBackground,
    surface = other_light_surface,
    onSurface = other_light_onSurface,
    surfaceVariant = other_light_surfaceVariant,
    onSurfaceVariant = other_light_onSurfaceVariant,
    outline = other_light_outline,
    inverseOnSurface = other_light_inverseOnSurface,
    inverseSurface = other_light_inverseSurface,
    inversePrimary = other_light_inversePrimary,
    surfaceTint = other_light_surfaceTint,
    outlineVariant = other_light_outlineVariant,
    scrim = other_light_scrim,
)


private val OtherDarkColors = darkColorScheme(
    primary = other_dark_primary,
    onPrimary = other_dark_onPrimary,
    primaryContainer = other_dark_primaryContainer,
    onPrimaryContainer = other_dark_onPrimaryContainer,
    secondary = other_dark_secondary,
    onSecondary = other_dark_onSecondary,
    secondaryContainer = other_dark_secondaryContainer,
    onSecondaryContainer = other_dark_onSecondaryContainer,
    tertiary = other_dark_tertiary,
    onTertiary = other_dark_onTertiary,
    tertiaryContainer = other_dark_tertiaryContainer,
    onTertiaryContainer = other_dark_onTertiaryContainer,
    error = other_dark_error,
    errorContainer = other_dark_errorContainer,
    onError = other_dark_onError,
    onErrorContainer = other_dark_onErrorContainer,
    background = other_dark_background,
    onBackground = other_dark_onBackground,
    surface = other_dark_surface,
    onSurface = other_dark_onSurface,
    surfaceVariant = other_dark_surfaceVariant,
    onSurfaceVariant = other_dark_onSurfaceVariant,
    outline = other_dark_outline,
    inverseOnSurface = other_dark_inverseOnSurface,
    inverseSurface = other_dark_inverseSurface,
    inversePrimary = other_dark_inversePrimary,
    surfaceTint = other_dark_surfaceTint,
    outlineVariant = other_dark_outlineVariant,
    scrim = other_dark_scrim,
)*/

@Composable
fun ScrimpTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
    )
}

/*fun getExpenseCategoryColorScheme(
    category: ExpenseCategory,
    isDarkTheme: Boolean
): ColorScheme = if (isDarkTheme) {
    when (category) {
        ExpenseCategory.ACCOMMODATION -> AccommodationDarkColors
        ExpenseCategory.TRANSPORTATION -> TransportationDarkColors
        ExpenseCategory.FOOD -> FoodDarkColors
        ExpenseCategory.ENTERTAINMENT -> EntertainmentDarkColors
        ExpenseCategory.SHOPPING -> ShoppingDarkColors
        ExpenseCategory.OTHER -> OtherDarkColors
    }
} else {
    when (category) {
        ExpenseCategory.ACCOMMODATION -> AccommodationLightColors
        ExpenseCategory.TRANSPORTATION -> TransportationLightColors
        ExpenseCategory.FOOD -> FoodLightColors
        ExpenseCategory.ENTERTAINMENT -> EntertainmentLightColors
        ExpenseCategory.SHOPPING -> ShoppingLightColors
        ExpenseCategory.OTHER -> OtherLightColors
    }
}*/

@Preview(showBackground = true)
@Composable
fun TypographyPreview() {
    val typographies = listOf(
        MaterialTheme.typography.displayLarge,
        MaterialTheme.typography.displayMedium,
        MaterialTheme.typography.displaySmall,
        MaterialTheme.typography.headlineLarge,
        MaterialTheme.typography.headlineMedium,
        MaterialTheme.typography.headlineSmall,
        MaterialTheme.typography.titleLarge,
        MaterialTheme.typography.titleMedium,
        MaterialTheme.typography.titleSmall,
        MaterialTheme.typography.bodyLarge,
        MaterialTheme.typography.bodyMedium,
        MaterialTheme.typography.bodySmall,
        MaterialTheme.typography.labelLarge,
        MaterialTheme.typography.labelMedium,
        MaterialTheme.typography.labelSmall,
    )

    Column {
        typographies.forEach {
            Text(
                text = "Sample text",
                style = it,
            )
        }
    }
}