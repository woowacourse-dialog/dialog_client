package com.on.dialog.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.on.dialog.designsystem.theme.preview.TypographyDarkPreview
import com.on.dialog.designsystem.theme.preview.TypographyPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val DialogTypography
    @Composable
    get() =
        Typography(
            displayLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 57.sp,
                    lineHeight = 64.sp,
                    letterSpacing = (-0.25).sp,
                ),
            displayMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 45.sp,
                    lineHeight = 52.sp,
                    letterSpacing = 0.sp,
                ),
            displaySmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 36.sp,
                    lineHeight = 44.sp,
                    letterSpacing = 0.sp,
                ),
            headlineLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    letterSpacing = 0.sp,
                ),
            headlineMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    lineHeight = 36.sp,
                    letterSpacing = 0.sp,
                ),
            headlineSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    letterSpacing = 0.sp,
                ),
            titleLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    letterSpacing = 0.sp,
                ),
            titleMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                ),
            titleSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                ),
            // Default Text Style
            bodyLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp,
                ),
            bodyMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                ),
            bodySmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                ),
            // Used for Button
            labelLarge =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                ),
            // Used for Navigation Items
            labelMedium =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.5.sp,
                ),
            // Used for Tag
            labelSmall =
                TextStyle(
                    fontFamily = pretendardFontFamily(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.5.sp,
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
