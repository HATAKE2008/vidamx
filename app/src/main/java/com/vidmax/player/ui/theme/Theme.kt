package com.vidmax.player.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 1. Classic (Default Dark Theme - Red/Gold)
val DefaultColors =
    darkColorScheme(
        primary = Color(0xFFF7B638), // Gold
        onPrimary = Color(0xFF0D0000),
        secondary = Color(0xFF780115), // Maroon
        background = Color(0xFF0D0000),
        surface = Color(0xFF1A0508),
        onBackground = Color(0xFFFFF5E0),
        onSurface = Color(0xFFFFF5E0),
        surfaceVariant = Color(0xFF240A0E),
        onSurfaceVariant = Color(0xFFBFA080))

// 2. Chartreuse & Gun Metal (Dark - PRO CONTRAST)
val ChartreuseColors =
    darkColorScheme(
        primary = Color(0xFFE1FF51),
        onPrimary = Color(0xFF00272C),
        secondary = Color(0xFF003840),
        background = Color(0xFF0B1A1C),
        surface = Color(0xFF122325),
        onBackground = Color(0xFFE1E3E3),
        onSurface = Color(0xFFE1E3E3),
        surfaceVariant = Color(0xFF1A3236),
        onSurfaceVariant = Color(0xFFBFC8C9))

// 3. Cyberpunk Neon (Dark - Vibrant Violet & Cyan)
val CyberpunkColors =
    darkColorScheme(
        primary = Color(0xFF00E5FF),
        onPrimary = Color(0xFF0A001A),
        secondary = Color(0xFFFF0055),
        background = Color(0xFF0A001A),
        surface = Color(0xFF150030),
        onBackground = Color(0xFFE0E0FF),
        onSurface = Color(0xFFE0E0FF),
        surfaceVariant = Color(0xFF26004D),
        onSurfaceVariant = Color(0xFFB399FF))

// 4. Cinematic Crimson (Dark - Pitch Black & Deep Red)
val CinematicColors =
    darkColorScheme(
        primary = Color(0xFFE50914),
        onPrimary = Color(0xFFFFFFFF),
        secondary = Color(0xFFB81D24),
        background = Color(0xFF000000),
        surface = Color(0xFF141414),
        onBackground = Color(0xFFFFFFFF),
        onSurface = Color(0xFFFFFFFF),
        surfaceVariant = Color(0xFF262626),
        onSurfaceVariant = Color(0xFFAAAAAA))

// 5. Aqua Breeze (Dark - Deep Blue & Teal)
val AquaBreezeColors =
    darkColorScheme(
        primary = Color(0xFF00D4FF),
        onPrimary = Color(0xFF001A22),
        secondary = Color(0xFF00A2C2),
        background = Color(0xFF05131A),
        surface = Color(0xFF0B212B),
        onBackground = Color(0xFFE6FBFF),
        onSurface = Color(0xFFE6FBFF),
        surfaceVariant = Color(0xFF133646),
        onSurfaceVariant = Color(0xFF90C2D6))

// 6. AMOLED Gold (Dark - Pure Black & Shiny Gold)
val AmoledGoldColors =
    darkColorScheme(
        primary = Color(0xFFFFD700),
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFFB8860B),
        background = Color(0xFF000000),
        surface = Color(0xFF0F0F0F),
        onBackground = Color(0xFFFFF8DC),
        onSurface = Color(0xFFFFF8DC),
        surfaceVariant = Color(0xFF1C1C1C),
        onSurfaceVariant = Color(0xFFD4AF37))

// 7. Pure Pitch Black (Full Dark) 🔥
val PureBlackColors =
    darkColorScheme(
        primary = Color(0xFFFFFFFF), // Pure White accents
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFFCCCCCC),
        background = Color(0xFF000000), // Pure Black
        surface = Color(0xFF000000), // Pure Black Surface
        onBackground = Color(0xFFFFFFFF),
        onSurface = Color(0xFFFFFFFF),
        surfaceVariant = Color(0xFF1A1A1A), // Very Dark Gray for Cards
        onSurfaceVariant = Color(0xFF999999))

// 8. Clean Pearl White (Full Light) 🔥
val PearlWhiteColors =
    lightColorScheme(
        primary = Color(0xFF000000), // Pure Black accents
        onPrimary = Color(0xFFFFFFFF),
        secondary = Color(0xFF333333),
        background = Color(0xFFFFFFFF), // Pure White
        surface = Color(0xFFFFFFFF), // Pure White Surface
        onBackground = Color(0xFF000000),
        onSurface = Color(0xFF000000),
        surfaceVariant = Color(0xFFF0F0F0), // Light Gray for Cards
        onSurfaceVariant = Color(0xFF666666))

// Theme State Enum
enum class AppTheme {
  DEFAULT_DARK,
  CHARTREUSE_GUNMETAL,
  CYBERPUNK_NEON,
  CINEMATIC_CRIMSON,
  AQUA_BREEZE,
  AMOLED_GOLD,
  PURE_PITCH_BLACK,
  CLEAN_PEARL_WHITE,
  DYNAMIC_COLOR
}

@Composable
fun VidMaxTheme(
    appTheme: AppTheme = AppTheme.DEFAULT_DARK,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
  val context = LocalContext.current

  val colorScheme =
      when {
        appTheme == AppTheme.DYNAMIC_COLOR && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        appTheme == AppTheme.DEFAULT_DARK -> DefaultColors
        appTheme == AppTheme.CHARTREUSE_GUNMETAL -> ChartreuseColors
        appTheme == AppTheme.CYBERPUNK_NEON -> CyberpunkColors
        appTheme == AppTheme.CINEMATIC_CRIMSON -> CinematicColors
        appTheme == AppTheme.AQUA_BREEZE -> AquaBreezeColors
        appTheme == AppTheme.AMOLED_GOLD -> AmoledGoldColors
        appTheme == AppTheme.PURE_PITCH_BLACK -> PureBlackColors
        appTheme == AppTheme.CLEAN_PEARL_WHITE -> PearlWhiteColors
        appTheme == AppTheme.DYNAMIC_COLOR -> DefaultColors
        else -> DefaultColors
      }

  MaterialTheme(colorScheme = colorScheme, content = content)
}
