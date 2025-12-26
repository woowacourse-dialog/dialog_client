package com.on.dialog.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.preview.TypographyDarkPreview
import com.on.dialog.designsystem.theme.preview.TypographyPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
private fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

internal val DialogTypography
    @Composable
    get() =
        Typography(
            displayLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(57.dp),
                    lineHeight = dpToSp(64.dp),
                    letterSpacing = dpToSp((-0.25).dp),
                ),
            displayMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(45.dp),
                    lineHeight = dpToSp(52.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            displaySmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(36.dp),
                    lineHeight = dpToSp(44.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            headlineLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(32.dp),
                    lineHeight = dpToSp(40.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            headlineMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(28.dp),
                    lineHeight = dpToSp(36.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            headlineSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(24.dp),
                    lineHeight = dpToSp(32.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            titleLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(22.dp),
                    lineHeight = dpToSp(28.dp),
                    letterSpacing = dpToSp(0.dp),
                ),
            titleMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(16.dp),
                    lineHeight = dpToSp(24.dp),
                    letterSpacing = dpToSp(0.15.dp),
                ),
            titleSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(14.dp),
                    lineHeight = dpToSp(20.dp),
                    letterSpacing = dpToSp(0.1.dp),
                ),
            // Default Text Style
            bodyLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(16.dp),
                    lineHeight = dpToSp(24.dp),
                    letterSpacing = dpToSp(0.5.dp),
                ),
            bodyMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(14.dp),
                    lineHeight = dpToSp(20.dp),
                    letterSpacing = dpToSp(0.25.dp),
                ),
            bodySmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = dpToSp(12.dp),
                    lineHeight = dpToSp(16.dp),
                    letterSpacing = dpToSp(0.4.dp),
                ),
            // Used for Button
            labelLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(14.dp),
                    lineHeight = dpToSp(20.dp),
                    letterSpacing = dpToSp(0.1.dp),
                ),
            // Used for Navigation Items
            labelMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(12.dp),
                    lineHeight = dpToSp(16.dp),
                    letterSpacing = dpToSp(0.5.dp),
                ),
            // Used for Tag
            labelSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(11.dp),
                    lineHeight = dpToSp(16.dp),
                    letterSpacing = dpToSp(0.5.dp),
                ),
        )

@Preview(name = "Light Typography", heightDp = 1200)
@Composable
internal fun TypographyPreview() {
    TypographyPreview()
}

@Preview(name = "Dark Typography", heightDp = 1200)
@Composable
internal fun TypographyDarkPreview() {
    TypographyDarkPreview()
}
