package com.glew.scrimp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.color.MaterialColors

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PositiveVariance = Color(0xFF69B96E)

@Composable
fun Color.harmonizeWithPrimary(): Color {
    return Color(MaterialColors.harmonize(this.toArgb(), MaterialTheme.colorScheme.primary.toArgb()))
}

/*val accommodation_light_primary = Color(0xFF1F6C2F)
val accommodation_light_onPrimary = Color(0xFFFFFFFF)
val accommodation_light_primaryContainer = Color(0xFFA6F5A8)
val accommodation_light_onPrimaryContainer = Color(0xFF002107)
val accommodation_light_secondary = Color(0xFF516350)
val accommodation_light_onSecondary = Color(0xFFFFFFFF)
val accommodation_light_secondaryContainer = Color(0xFFD4E8D0)
val accommodation_light_onSecondaryContainer = Color(0xFF101F10)
val accommodation_light_tertiary = Color(0xFF39656C)
val accommodation_light_onTertiary = Color(0xFFFFFFFF)
val accommodation_light_tertiaryContainer = Color(0xFFBCEBF2)
val accommodation_light_onTertiaryContainer = Color(0xFF001F23)
val accommodation_light_error = Color(0xFFBA1A1A)
val accommodation_light_errorContainer = Color(0xFFFFDAD6)
val accommodation_light_onError = Color(0xFFFFFFFF)
val accommodation_light_onErrorContainer = Color(0xFF410002)
val accommodation_light_background = Color(0xFFFCFDF7)
val accommodation_light_onBackground = Color(0xFF1A1C19)
val accommodation_light_surface = Color(0xFFFCFDF7)
val accommodation_light_onSurface = Color(0xFF1A1C19)
val accommodation_light_surfaceVariant = Color(0xFFDEE5D9)
val accommodation_light_onSurfaceVariant = Color(0xFF424940)
val accommodation_light_outline = Color(0xFF72796F)
val accommodation_light_inverseOnSurface = Color(0xFFF0F1EB)
val accommodation_light_inverseSurface = Color(0xFF2F312D)
val accommodation_light_inversePrimary = Color(0xFF8AD88E)
val accommodation_light_shadow = Color(0xFF000000)
val accommodation_light_surfaceTint = Color(0xFF1F6C2F)
val accommodation_light_outlineVariant = Color(0xFFC2C9BD)
val accommodation_light_scrim = Color(0xFF000000)

val accommodation_dark_primary = Color(0xFF8AD88E)
val accommodation_dark_onPrimary = Color(0xFF003911)
val accommodation_dark_primaryContainer = Color(0xFF00531C)
val accommodation_dark_onPrimaryContainer = Color(0xFFA6F5A8)
val accommodation_dark_secondary = Color(0xFFB9CCB5)
val accommodation_dark_onSecondary = Color(0xFF243424)
val accommodation_dark_secondaryContainer = Color(0xFF3A4B39)
val accommodation_dark_onSecondaryContainer = Color(0xFFD4E8D0)
val accommodation_dark_tertiary = Color(0xFFA1CED6)
val accommodation_dark_onTertiary = Color(0xFF00363C)
val accommodation_dark_tertiaryContainer = Color(0xFF1F4D53)
val accommodation_dark_onTertiaryContainer = Color(0xFFBCEBF2)
val accommodation_dark_error = Color(0xFFFFB4AB)
val accommodation_dark_errorContainer = Color(0xFF93000A)
val accommodation_dark_onError = Color(0xFF690005)
val accommodation_dark_onErrorContainer = Color(0xFFFFDAD6)
val accommodation_dark_background = Color(0xFF1A1C19)
val accommodation_dark_onBackground = Color(0xFFE2E3DD)
val accommodation_dark_surface = Color(0xFF1A1C19)
val accommodation_dark_onSurface = Color(0xFFE2E3DD)
val accommodation_dark_surfaceVariant = Color(0xFF424940)
val accommodation_dark_onSurfaceVariant = Color(0xFFC2C9BD)
val accommodation_dark_outline = Color(0xFF8C9389)
val accommodation_dark_inverseOnSurface = Color(0xFF1A1C19)
val accommodation_dark_inverseSurface = Color(0xFFE2E3DD)
val accommodation_dark_inversePrimary = Color(0xFF1F6C2F)
val accommodation_dark_shadow = Color(0xFF000000)
val accommodation_dark_surfaceTint = Color(0xFF8AD88E)
val accommodation_dark_outlineVariant = Color(0xFF424940)
val accommodation_dark_scrim = Color(0xFF000000)

val transportation_light_primary = Color(0xFF006398)
val transportation_light_onPrimary = Color(0xFFFFFFFF)
val transportation_light_primaryContainer = Color(0xFFCDE5FF)
val transportation_light_onPrimaryContainer = Color(0xFF001D32)
val transportation_light_secondary = Color(0xFF51606F)
val transportation_light_onSecondary = Color(0xFFFFFFFF)
val transportation_light_secondaryContainer = Color(0xFFD4E4F6)
val transportation_light_onSecondaryContainer = Color(0xFF0D1D2A)
val transportation_light_tertiary = Color(0xFF67587A)
val transportation_light_onTertiary = Color(0xFFFFFFFF)
val transportation_light_tertiaryContainer = Color(0xFFEDDCFF)
val transportation_light_onTertiaryContainer = Color(0xFF221533)
val transportation_light_error = Color(0xFFBA1A1A)
val transportation_light_errorContainer = Color(0xFFFFDAD6)
val transportation_light_onError = Color(0xFFFFFFFF)
val transportation_light_onErrorContainer = Color(0xFF410002)
val transportation_light_background = Color(0xFFFCFCFF)
val transportation_light_onBackground = Color(0xFF1A1C1E)
val transportation_light_surface = Color(0xFFFCFCFF)
val transportation_light_onSurface = Color(0xFF1A1C1E)
val transportation_light_surfaceVariant = Color(0xFFDEE3EB)
val transportation_light_onSurfaceVariant = Color(0xFF42474E)
val transportation_light_outline = Color(0xFF72787E)
val transportation_light_inverseOnSurface = Color(0xFFF0F0F4)
val transportation_light_inverseSurface = Color(0xFF2F3033)
val transportation_light_inversePrimary = Color(0xFF94CCFF)
val transportation_light_shadow = Color(0xFF000000)
val transportation_light_surfaceTint = Color(0xFF006398)
val transportation_light_outlineVariant = Color(0xFFC2C7CE)
val transportation_light_scrim = Color(0xFF000000)

val transportation_dark_primary = Color(0xFF94CCFF)
val transportation_dark_onPrimary = Color(0xFF003352)
val transportation_dark_primaryContainer = Color(0xFF004B74)
val transportation_dark_onPrimaryContainer = Color(0xFFCDE5FF)
val transportation_dark_secondary = Color(0xFFB8C8DA)
val transportation_dark_onSecondary = Color(0xFF233240)
val transportation_dark_secondaryContainer = Color(0xFF394857)
val transportation_dark_onSecondaryContainer = Color(0xFFD4E4F6)
val transportation_dark_tertiary = Color(0xFFD2BFE7)
val transportation_dark_onTertiary = Color(0xFF382A4A)
val transportation_dark_tertiaryContainer = Color(0xFF4F4061)
val transportation_dark_onTertiaryContainer = Color(0xFFEDDCFF)
val transportation_dark_error = Color(0xFFFFB4AB)
val transportation_dark_errorContainer = Color(0xFF93000A)
val transportation_dark_onError = Color(0xFF690005)
val transportation_dark_onErrorContainer = Color(0xFFFFDAD6)
val transportation_dark_background = Color(0xFF1A1C1E)
val transportation_dark_onBackground = Color(0xFFE2E2E5)
val transportation_dark_surface = Color(0xFF1A1C1E)
val transportation_dark_onSurface = Color(0xFFE2E2E5)
val transportation_dark_surfaceVariant = Color(0xFF42474E)
val transportation_dark_onSurfaceVariant = Color(0xFFC2C7CE)
val transportation_dark_outline = Color(0xFF8C9198)
val transportation_dark_inverseOnSurface = Color(0xFF1A1C1E)
val transportation_dark_inverseSurface = Color(0xFFE2E2E5)
val transportation_dark_inversePrimary = Color(0xFF006398)
val transportation_dark_shadow = Color(0xFF000000)
val transportation_dark_surfaceTint = Color(0xFF94CCFF)
val transportation_dark_outlineVariant = Color(0xFF42474E)
val transportation_dark_scrim = Color(0xFF000000)

val food_light_primary = Color(0xFF835500)
val food_light_onPrimary = Color(0xFFFFFFFF)
val food_light_primaryContainer = Color(0xFFFFDDB4)
val food_light_onPrimaryContainer = Color(0xFF291800)
val food_light_secondary = Color(0xFF705B40)
val food_light_onSecondary = Color(0xFFFFFFFF)
val food_light_secondaryContainer = Color(0xFFFBDEBC)
val food_light_onSecondaryContainer = Color(0xFF271904)
val food_light_tertiary = Color(0xFF52643F)
val food_light_onTertiary = Color(0xFFFFFFFF)
val food_light_tertiaryContainer = Color(0xFFD5EABB)
val food_light_onTertiaryContainer = Color(0xFF111F03)
val food_light_error = Color(0xFFBA1A1A)
val food_light_errorContainer = Color(0xFFFFDAD6)
val food_light_onError = Color(0xFFFFFFFF)
val food_light_onErrorContainer = Color(0xFF410002)
val food_light_background = Color(0xFFFFFBFF)
val food_light_onBackground = Color(0xFF1F1B16)
val food_light_surface = Color(0xFFFFFBFF)
val food_light_onSurface = Color(0xFF1F1B16)
val food_light_surfaceVariant = Color(0xFFF0E0D0)
val food_light_onSurfaceVariant = Color(0xFF4F4539)
val food_light_outline = Color(0xFF817567)
val food_light_inverseOnSurface = Color(0xFFF9EFE7)
val food_light_inverseSurface = Color(0xFF34302A)
val food_light_inversePrimary = Color(0xFFFFB954)
val food_light_shadow = Color(0xFF000000)
val food_light_surfaceTint = Color(0xFF835500)
val food_light_outlineVariant = Color(0xFFD3C4B4)
val food_light_scrim = Color(0xFF000000)

val food_dark_primary = Color(0xFFFFB954)
val food_dark_onPrimary = Color(0xFF452B00)
val food_dark_primaryContainer = Color(0xFF633F00)
val food_dark_onPrimaryContainer = Color(0xFFFFDDB4)
val food_dark_secondary = Color(0xFFDEC2A1)
val food_dark_onSecondary = Color(0xFF3E2D16)
val food_dark_secondaryContainer = Color(0xFF56432B)
val food_dark_onSecondaryContainer = Color(0xFFFBDEBC)
val food_dark_tertiary = Color(0xFFB9CDA0)
val food_dark_onTertiary = Color(0xFF253515)
val food_dark_tertiaryContainer = Color(0xFF3B4C29)
val food_dark_onTertiaryContainer = Color(0xFFD5EABB)
val food_dark_error = Color(0xFFFFB4AB)
val food_dark_errorContainer = Color(0xFF93000A)
val food_dark_onError = Color(0xFF690005)
val food_dark_onErrorContainer = Color(0xFFFFDAD6)
val food_dark_background = Color(0xFF1F1B16)
val food_dark_onBackground = Color(0xFFEAE1D9)
val food_dark_surface = Color(0xFF1F1B16)
val food_dark_onSurface = Color(0xFFEAE1D9)
val food_dark_surfaceVariant = Color(0xFF4F4539)
val food_dark_onSurfaceVariant = Color(0xFFD3C4B4)
val food_dark_outline = Color(0xFF9C8F80)
val food_dark_inverseOnSurface = Color(0xFF1F1B16)
val food_dark_inverseSurface = Color(0xFFEAE1D9)
val food_dark_inversePrimary = Color(0xFF835500)
val food_dark_shadow = Color(0xFF000000)
val food_dark_surfaceTint = Color(0xFFFFB954)
val food_dark_outlineVariant = Color(0xFF4F4539)
val food_dark_scrim = Color(0xFF000000)

val entertainment_light_primary = Color(0xFFAB2C5D)
val entertainment_light_onPrimary = Color(0xFFFFFFFF)
val entertainment_light_primaryContainer = Color(0xFFFFD9E1)
val entertainment_light_onPrimaryContainer = Color(0xFF3F001B)
val entertainment_light_secondary = Color(0xFF74565E)
val entertainment_light_onSecondary = Color(0xFFFFFFFF)
val entertainment_light_secondaryContainer = Color(0xFFFFD9E1)
val entertainment_light_onSecondaryContainer = Color(0xFF2B151B)
val entertainment_light_tertiary = Color(0xFF7B5734)
val entertainment_light_onTertiary = Color(0xFFFFFFFF)
val entertainment_light_tertiaryContainer = Color(0xFFFFDCC0)
val entertainment_light_onTertiaryContainer = Color(0xFF2D1600)
val entertainment_light_error = Color(0xFFBA1A1A)
val entertainment_light_errorContainer = Color(0xFFFFDAD6)
val entertainment_light_onError = Color(0xFFFFFFFF)
val entertainment_light_onErrorContainer = Color(0xFF410002)
val entertainment_light_background = Color(0xFFFFFBFF)
val entertainment_light_onBackground = Color(0xFF201A1B)
val entertainment_light_surface = Color(0xFFFFFBFF)
val entertainment_light_onSurface = Color(0xFF201A1B)
val entertainment_light_surfaceVariant = Color(0xFFF3DDE1)
val entertainment_light_onSurfaceVariant = Color(0xFF514346)
val entertainment_light_outline = Color(0xFF847376)
val entertainment_light_inverseOnSurface = Color(0xFFFAEEEF)
val entertainment_light_inverseSurface = Color(0xFF352F30)
val entertainment_light_inversePrimary = Color(0xFFFFB1C5)
val entertainment_light_shadow = Color(0xFF000000)
val entertainment_light_surfaceTint = Color(0xFFAB2C5D)
val entertainment_light_outlineVariant = Color(0xFFD6C2C5)
val entertainment_light_scrim = Color(0xFF000000)

val entertainment_dark_primary = Color(0xFFFFB1C5)
val entertainment_dark_onPrimary = Color(0xFF65002F)
val entertainment_dark_primaryContainer = Color(0xFF8B0E45)
val entertainment_dark_onPrimaryContainer = Color(0xFFFFD9E1)
val entertainment_dark_secondary = Color(0xFFE3BDC5)
val entertainment_dark_onSecondary = Color(0xFF422930)
val entertainment_dark_secondaryContainer = Color(0xFF5B3F46)
val entertainment_dark_onSecondaryContainer = Color(0xFFFFD9E1)
val entertainment_dark_tertiary = Color(0xFFEEBD92)
val entertainment_dark_onTertiary = Color(0xFF472A0A)
val entertainment_dark_tertiaryContainer = Color(0xFF613F1F)
val entertainment_dark_onTertiaryContainer = Color(0xFFFFDCC0)
val entertainment_dark_error = Color(0xFFFFB4AB)
val entertainment_dark_errorContainer = Color(0xFF93000A)
val entertainment_dark_onError = Color(0xFF690005)
val entertainment_dark_onErrorContainer = Color(0xFFFFDAD6)
val entertainment_dark_background = Color(0xFF201A1B)
val entertainment_dark_onBackground = Color(0xFFECE0E1)
val entertainment_dark_surface = Color(0xFF201A1B)
val entertainment_dark_onSurface = Color(0xFFECE0E1)
val entertainment_dark_surfaceVariant = Color(0xFF514346)
val entertainment_dark_onSurfaceVariant = Color(0xFFD6C2C5)
val entertainment_dark_outline = Color(0xFF9E8C90)
val entertainment_dark_inverseOnSurface = Color(0xFF201A1B)
val entertainment_dark_inverseSurface = Color(0xFFECE0E1)
val entertainment_dark_inversePrimary = Color(0xFFAB2C5D)
val entertainment_dark_shadow = Color(0xFF000000)
val entertainment_dark_surfaceTint = Color(0xFFFFB1C5)
val entertainment_dark_outlineVariant = Color(0xFF514346)
val entertainment_dark_scrim = Color(0xFF000000)

val shopping_light_primary = Color(0xFF6D4EA2)
val shopping_light_onPrimary = Color(0xFFFFFFFF)
val shopping_light_primaryContainer = Color(0xFFEBDCFF)
val shopping_light_onPrimaryContainer = Color(0xFF270058)
val shopping_light_secondary = Color(0xFF635B70)
val shopping_light_onSecondary = Color(0xFFFFFFFF)
val shopping_light_secondaryContainer = Color(0xFFEADEF7)
val shopping_light_onSecondaryContainer = Color(0xFF1F182A)
val shopping_light_tertiary = Color(0xFF7F525D)
val shopping_light_onTertiary = Color(0xFFFFFFFF)
val shopping_light_tertiaryContainer = Color(0xFFFFD9E0)
val shopping_light_onTertiaryContainer = Color(0xFF32101A)
val shopping_light_error = Color(0xFFBA1A1A)
val shopping_light_errorContainer = Color(0xFFFFDAD6)
val shopping_light_onError = Color(0xFFFFFFFF)
val shopping_light_onErrorContainer = Color(0xFF410002)
val shopping_light_background = Color(0xFFFFFBFF)
val shopping_light_onBackground = Color(0xFF1D1B1E)
val shopping_light_surface = Color(0xFFFFFBFF)
val shopping_light_onSurface = Color(0xFF1D1B1E)
val shopping_light_surfaceVariant = Color(0xFFE8E0EB)
val shopping_light_onSurfaceVariant = Color(0xFF49454E)
val shopping_light_outline = Color(0xFF7A757F)
val shopping_light_inverseOnSurface = Color(0xFFF5EFF4)
val shopping_light_inverseSurface = Color(0xFF323033)
val shopping_light_inversePrimary = Color(0xFFD4BBFF)
val shopping_light_shadow = Color(0xFF000000)
val shopping_light_surfaceTint = Color(0xFF6D4EA2)
val shopping_light_outlineVariant = Color(0xFFCBC4CF)
val shopping_light_scrim = Color(0xFF000000)

val shopping_dark_primary = Color(0xFFD4BBFF)
val shopping_dark_onPrimary = Color(0xFF3D1C71)
val shopping_dark_primaryContainer = Color(0xFF543589)
val shopping_dark_onPrimaryContainer = Color(0xFFEBDCFF)
val shopping_dark_secondary = Color(0xFFCDC2DB)
val shopping_dark_onSecondary = Color(0xFF342D40)
val shopping_dark_secondaryContainer = Color(0xFF4B4358)
val shopping_dark_onSecondaryContainer = Color(0xFFEADEF7)
val shopping_dark_tertiary = Color(0xFFF1B7C4)
val shopping_dark_onTertiary = Color(0xFF4A252F)
val shopping_dark_tertiaryContainer = Color(0xFF643B45)
val shopping_dark_onTertiaryContainer = Color(0xFFFFD9E0)
val shopping_dark_error = Color(0xFFFFB4AB)
val shopping_dark_errorContainer = Color(0xFF93000A)
val shopping_dark_onError = Color(0xFF690005)
val shopping_dark_onErrorContainer = Color(0xFFFFDAD6)
val shopping_dark_background = Color(0xFF1D1B1E)
val shopping_dark_onBackground = Color(0xFFE6E1E6)
val shopping_dark_surface = Color(0xFF1D1B1E)
val shopping_dark_onSurface = Color(0xFFE6E1E6)
val shopping_dark_surfaceVariant = Color(0xFF49454E)
val shopping_dark_onSurfaceVariant = Color(0xFFCBC4CF)
val shopping_dark_outline = Color(0xFF948E99)
val shopping_dark_inverseOnSurface = Color(0xFF1D1B1E)
val shopping_dark_inverseSurface = Color(0xFFE6E1E6)
val shopping_dark_inversePrimary = Color(0xFF6D4EA2)
val shopping_dark_shadow = Color(0xFF000000)
val shopping_dark_surfaceTint = Color(0xFFD4BBFF)
val shopping_dark_outlineVariant = Color(0xFF49454E)
val shopping_dark_scrim = Color(0xFF000000)

val other_light_primary = Color(0xFF006782)
val other_light_onPrimary = Color(0xFFFFFFFF)
val other_light_primaryContainer = Color(0xFFBBE9FF)
val other_light_onPrimaryContainer = Color(0xFF001F29)
val other_light_secondary = Color(0xFF4C616B)
val other_light_onSecondary = Color(0xFFFFFFFF)
val other_light_secondaryContainer = Color(0xFFCFE6F2)
val other_light_onSecondaryContainer = Color(0xFF081E26)
val other_light_tertiary = Color(0xFF5C5B7D)
val other_light_onTertiary = Color(0xFFFFFFFF)
val other_light_tertiaryContainer = Color(0xFFE2DFFF)
val other_light_onTertiaryContainer = Color(0xFF191837)
val other_light_error = Color(0xFFBA1A1A)
val other_light_errorContainer = Color(0xFFFFDAD6)
val other_light_onError = Color(0xFFFFFFFF)
val other_light_onErrorContainer = Color(0xFF410002)
val other_light_background = Color(0xFFFBFCFE)
val other_light_onBackground = Color(0xFF191C1E)
val other_light_surface = Color(0xFFFBFCFE)
val other_light_onSurface = Color(0xFF191C1E)
val other_light_surfaceVariant = Color(0xFFDCE4E9)
val other_light_onSurfaceVariant = Color(0xFF40484C)
val other_light_outline = Color(0xFF70787D)
val other_light_inverseOnSurface = Color(0xFFEFF1F3)
val other_light_inverseSurface = Color(0xFF2E3132)
val other_light_inversePrimary = Color(0xFF61D4FF)
val other_light_shadow = Color(0xFF000000)
val other_light_surfaceTint = Color(0xFF006782)
val other_light_outlineVariant = Color(0xFFC0C8CC)
val other_light_scrim = Color(0xFF000000)

val other_dark_primary = Color(0xFF61D4FF)
val other_dark_onPrimary = Color(0xFF003545)
val other_dark_primaryContainer = Color(0xFF004D63)
val other_dark_onPrimaryContainer = Color(0xFFBBE9FF)
val other_dark_secondary = Color(0xFFB4CAD5)
val other_dark_onSecondary = Color(0xFF1E333C)
val other_dark_secondaryContainer = Color(0xFF354A53)
val other_dark_onSecondaryContainer = Color(0xFFCFE6F2)
val other_dark_tertiary = Color(0xFFC5C3EA)
val other_dark_onTertiary = Color(0xFF2E2D4D)
val other_dark_tertiaryContainer = Color(0xFF444364)
val other_dark_onTertiaryContainer = Color(0xFFE2DFFF)
val other_dark_error = Color(0xFFFFB4AB)
val other_dark_errorContainer = Color(0xFF93000A)
val other_dark_onError = Color(0xFF690005)
val other_dark_onErrorContainer = Color(0xFFFFDAD6)
val other_dark_background = Color(0xFF191C1E)
val other_dark_onBackground = Color(0xFFE1E3E4)
val other_dark_surface = Color(0xFF191C1E)
val other_dark_onSurface = Color(0xFFE1E3E4)
val other_dark_surfaceVariant = Color(0xFF40484C)
val other_dark_onSurfaceVariant = Color(0xFFC0C8CC)
val other_dark_outline = Color(0xFF8A9296)
val other_dark_inverseOnSurface = Color(0xFF191C1E)
val other_dark_inverseSurface = Color(0xFFE1E3E4)
val other_dark_inversePrimary = Color(0xFF006782)
val other_dark_shadow = Color(0xFF000000)
val other_dark_surfaceTint = Color(0xFF61D4FF)
val other_dark_outlineVariant = Color(0xFF40484C)
val other_dark_scrim = Color(0xFF000000)*/