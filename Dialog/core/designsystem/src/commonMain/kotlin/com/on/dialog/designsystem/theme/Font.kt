package com.on.dialog.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dialog.core.designsystem.generated.resources.Res
import dialog.core.designsystem.generated.resources.pretendard_bold
import dialog.core.designsystem.generated.resources.pretendard_light
import dialog.core.designsystem.generated.resources.pretendard_medium
import dialog.core.designsystem.generated.resources.pretendard_regular
import dialog.core.designsystem.generated.resources.pretendard_semibold
import org.jetbrains.compose.resources.Font

@Composable
internal fun pretendardFontFamily() =
    FontFamily(
        Font(Res.font.pretendard_light, FontWeight.Light),
        Font(Res.font.pretendard_medium, FontWeight.Medium),
        Font(Res.font.pretendard_regular, FontWeight.Normal),
        Font(Res.font.pretendard_semibold, FontWeight.SemiBold),
        Font(Res.font.pretendard_bold, FontWeight.Bold),
    )
