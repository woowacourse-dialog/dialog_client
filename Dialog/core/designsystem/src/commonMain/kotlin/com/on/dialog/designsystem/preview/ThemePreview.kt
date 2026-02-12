package com.on.dialog.designsystem.preview

import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "LightTheme",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "DarkTheme",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
annotation class ThemePreview
