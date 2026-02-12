package com.on.dialog.designsystem.preview

import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "LightTheme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_TYPE_NORMAL or AndroidUiModes.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "DarkTheme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_TYPE_NORMAL or AndroidUiModes.UI_MODE_NIGHT_YES,
)
annotation class ThemePreview
