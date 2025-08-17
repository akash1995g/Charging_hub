package com.akashg.androidapp.charginghub.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
)

data class CustomColors(
    val textDark: Color = black,
    val primaryText: Color = text,
    val whiteColor: Color = Color.White,
    val redColor: Color = red,
    val blueColor: Color = blue,
    val yellowColor: Color = yellow,
    val orangeColor: Color = orange,
    val greenColor: Color = green,
    val borderColor: Color = border,
    val backGroundColor: Color = backGround,
    val secondaryBackGroundColor: Color = secondaryBackGround,
)

val DarkCustomColors = CustomColors(
    textDark = Color(0xFFFAFAFA),
    primaryText = Color(0xFFFFFFFF),
    whiteColor = Color.Black,
    redColor = Color(0xFFFF453A),
    blueColor = Color(0xFF0A84FF),
    yellowColor = Color(0xFFFFD60A),
    orangeColor = Color(0xFFFF9F0A),
    greenColor = Color(0xFF30D158),
    borderColor = Color(0xE1B49B9B),
    backGroundColor = Color(0xFF676770),
    secondaryBackGroundColor = Color(0xFF2C2C2E),
)

val LightCustomColors = CustomColors()

val LocalCustomColors = staticCompositionLocalOf { CustomColors() }

val MaterialTheme.customColors: CustomColors
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current

@Composable
fun ChargingHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalCustomColors provides if (darkTheme) DarkCustomColors else LightCustomColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
