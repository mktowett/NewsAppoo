package io.github.joelkanyi.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme =
    darkColorScheme(
        primary = Color.White,
        secondary = PrimaryColor,
        tertiary = PrimaryColor
    )

private val LightColorScheme =
    lightColorScheme(
        primary = PrimaryColor,
        secondary = PrimaryColor,
        tertiary = PrimaryColor
    )

@Suppress("DEPRECATION")
@Composable
fun NewsAppTheme(
    theme: Int = Theme.FOLLOW_SYSTEM.themeValue,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val autoColors = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    // Dynamic color is available on Android 12+
    val dynamicColors = if (supportsDynamicTheming() && dynamicColor) {
        if (isSystemInDarkTheme()) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        autoColors
    }

    val colorScheme = when (theme) {
        Theme.LIGHT_THEME.themeValue -> LightColorScheme
        Theme.DARK_THEME.themeValue -> DarkColorScheme
        Theme.MATERIAL_YOU.themeValue -> dynamicColors
        else -> autoColors
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = colorScheme.background
        )
        systemUiController.setNavigationBarColor(
            color = colorScheme.background
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

enum class Theme(
    val themeValue: Int,
) {
    MATERIAL_YOU(
        themeValue = 12,
    ),
    FOLLOW_SYSTEM(
        themeValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
    ),
    LIGHT_THEME(
        themeValue = AppCompatDelegate.MODE_NIGHT_NO,
    ),
    DARK_THEME(
        themeValue = AppCompatDelegate.MODE_NIGHT_YES,
    ),
}
