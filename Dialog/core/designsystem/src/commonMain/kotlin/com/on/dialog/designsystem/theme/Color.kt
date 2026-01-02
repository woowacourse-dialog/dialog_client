package com.on.dialog.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val primaryLight = Color(0xFF1B2838)
internal val onPrimaryLight = Color(0xFFFFFFFF)
internal val primaryContainerLight = Color(0xFF313E4F)
internal val onPrimaryContainerLight = Color(0xFF9BA9BD)
internal val secondaryLight = Color(0xFF5A5F67)
internal val onSecondaryLight = Color(0xFFFFFFFF)
internal val secondaryContainerLight = Color(0xFFDCE0E9)
internal val onSecondaryContainerLight = Color(0xFF5E636B)
internal val tertiaryLight = Color(0xFF332232)
internal val onTertiaryLight = Color(0xFFFFFFFF)
internal val tertiaryContainerLight = Color(0xFF4A3748)
internal val onTertiaryContainerLight = Color(0xFFBAA0B5)
internal val errorLight = Color(0xFFBA1A1A)
internal val onErrorLight = Color(0xFFFFFFFF)
internal val errorContainerLight = Color(0xFFFFDAD6)
internal val onErrorContainerLight = Color(0xFF93000A)
internal val backgroundLight = Color(0xFFFBF9FA)
internal val onBackgroundLight = Color(0xFF1B1B1D)
internal val surfaceLight = Color(0xFFFBF9FA)
internal val onSurfaceLight = Color(0xFF1B1B1D)
internal val surfaceVariantLight = Color(0xFFE1E2E9)
internal val onSurfaceVariantLight = Color(0xFF44474C)
internal val outlineLight = Color(0xFF75777D)
internal val outlineVariantLight = Color(0xFFC4C6CC)
internal val scrimLight = Color(0xFF000000)
internal val inverseSurfaceLight = Color(0xFF303032)
internal val inverseOnSurfaceLight = Color(0xFFF2F0F1)
internal val inversePrimaryLight = Color(0xFFBAC7DD)
internal val surfaceDimLight = Color(0xFFDBD9DB)
internal val surfaceBrightLight = Color(0xFFFBF9FA)
internal val surfaceContainerLowestLight = Color(0xFFFFFFFF)
internal val surfaceContainerLowLight = Color(0xFFF5F3F4)
internal val surfaceContainerLight = Color(0xFFF0EDEF)
internal val surfaceContainerHighLight = Color(0xFFEAE7E9)
internal val surfaceContainerHighestLight = Color(0xFFE4E2E3)

internal val primaryDark = Color(0xFFBAC7DD)
internal val onPrimaryDark = Color(0xFF243142)
internal val primaryContainerDark = Color(0xFF313E4F)
internal val onPrimaryContainerDark = Color(0xFF9BA9BD)
internal val secondaryDark = Color(0xFFC2C7D0)
internal val onSecondaryDark = Color(0xFF2C3138)
internal val secondaryContainerDark = Color(0xFF42474F)
internal val onSecondaryContainerDark = Color(0xFFB1B5BE)
internal val tertiaryDark = Color(0xFFD9BFD4)
internal val onTertiaryDark = Color(0xFF3D2B3B)
internal val tertiaryContainerDark = Color(0xFF4A3748)
internal val onTertiaryContainerDark = Color(0xFFBAA0B5)
internal val errorDark = Color(0xFFFFB4AB)
internal val onErrorDark = Color(0xFF690005)
internal val errorContainerDark = Color(0xFF93000A)
internal val onErrorContainerDark = Color(0xFFFFDAD6)
internal val backgroundDark = Color(0xFF131315)
internal val onBackgroundDark = Color(0xFFE4E2E3)
internal val surfaceDark = Color(0xFF131315)
internal val onSurfaceDark = Color(0xFFE4E2E3)
internal val surfaceVariantDark = Color(0xFF44474C)
internal val onSurfaceVariantDark = Color(0xFFC4C6CC)
internal val outlineDark = Color(0xFF8E9196)
internal val outlineVariantDark = Color(0xFF44474C)
internal val scrimDark = Color(0xFF000000)
internal val inverseSurfaceDark = Color(0xFFE4E2E3)
internal val inverseOnSurfaceDark = Color(0xFF303032)
internal val inversePrimaryDark = Color(0xFF525F72)
internal val surfaceDimDark = Color(0xFF131315)
internal val surfaceBrightDark = Color(0xFF39393A)
internal val surfaceContainerLowestDark = Color(0xFF0E0E0F)
internal val surfaceContainerLowDark = Color(0xFF1B1B1D)
internal val surfaceContainerDark = Color(0xFF1F1F21)
internal val surfaceContainerHighDark = Color(0xFF2A2A2B)
internal val surfaceContainerHighestDark = Color(0xFF343536)

internal val Gray200 = Color(0xFFEEEEEE)
internal val Gray400 = Color(0xFFBDBDBD)

@Preview(name = "Light Colors", heightDp = 2000)
@Composable
private fun ColorPalettePreview() {
    DialogTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ColorPaletteContent()
        }
    }
}

@Preview(name = "Dark Colors", heightDp = 2000)
@Composable
private fun ColorPaletteDarkPreview() {
    DialogTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ColorPaletteContent()
        }
    }
}

@Composable
private fun ColorPaletteContent() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Color Palette",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        ColorGroup(title = "Primary") {
            ColorItem("primary", MaterialTheme.colorScheme.primary)
            ColorItem("onPrimary", MaterialTheme.colorScheme.onPrimary)
            ColorItem("primaryContainer", MaterialTheme.colorScheme.primaryContainer)
            ColorItem("onPrimaryContainer", MaterialTheme.colorScheme.onPrimaryContainer)
        }

        ColorGroup(title = "Secondary") {
            ColorItem("secondary", MaterialTheme.colorScheme.secondary)
            ColorItem("onSecondary", MaterialTheme.colorScheme.onSecondary)
            ColorItem("secondaryContainer", MaterialTheme.colorScheme.secondaryContainer)
            ColorItem("onSecondaryContainer", MaterialTheme.colorScheme.onSecondaryContainer)
        }

        ColorGroup(title = "Tertiary") {
            ColorItem("tertiary", MaterialTheme.colorScheme.tertiary)
            ColorItem("onTertiary", MaterialTheme.colorScheme.onTertiary)
            ColorItem("tertiaryContainer", MaterialTheme.colorScheme.tertiaryContainer)
            ColorItem("onTertiaryContainer", MaterialTheme.colorScheme.onTertiaryContainer)
        }

        ColorGroup(title = "Error") {
            ColorItem("error", MaterialTheme.colorScheme.error)
            ColorItem("onError", MaterialTheme.colorScheme.onError)
            ColorItem("errorContainer", MaterialTheme.colorScheme.errorContainer)
            ColorItem("onErrorContainer", MaterialTheme.colorScheme.onErrorContainer)
        }

        ColorGroup(title = "Background & Surface") {
            ColorItem("background", MaterialTheme.colorScheme.background)
            ColorItem("onBackground", MaterialTheme.colorScheme.onBackground)
            ColorItem("surface", MaterialTheme.colorScheme.surface)
            ColorItem("onSurface", MaterialTheme.colorScheme.onSurface)
            ColorItem("surfaceVariant", MaterialTheme.colorScheme.surfaceVariant)
            ColorItem("onSurfaceVariant", MaterialTheme.colorScheme.onSurfaceVariant)
        }

        ColorGroup(title = "Surface Containers") {
            ColorItem("surfaceDim", MaterialTheme.colorScheme.surfaceDim)
            ColorItem("surfaceBright", MaterialTheme.colorScheme.surfaceBright)
            ColorItem("surfaceContainerLowest", MaterialTheme.colorScheme.surfaceContainerLowest)
            ColorItem("surfaceContainerLow", MaterialTheme.colorScheme.surfaceContainerLow)
            ColorItem("surfaceContainer", MaterialTheme.colorScheme.surfaceContainer)
            ColorItem("surfaceContainerHigh", MaterialTheme.colorScheme.surfaceContainerHigh)
            ColorItem("surfaceContainerHighest", MaterialTheme.colorScheme.surfaceContainerHighest)
        }

        ColorGroup(title = "Outline & Inverse") {
            ColorItem("outline", MaterialTheme.colorScheme.outline)
            ColorItem("outlineVariant", MaterialTheme.colorScheme.outlineVariant)
            ColorItem("inverseSurface", MaterialTheme.colorScheme.inverseSurface)
            ColorItem("inverseOnSurface", MaterialTheme.colorScheme.inverseOnSurface)
            ColorItem("inversePrimary", MaterialTheme.colorScheme.inversePrimary)
            ColorItem("scrim", MaterialTheme.colorScheme.scrim)
        }
    }
}

@Composable
private fun ColorGroup(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp),
        )
        content()
    }
}

@Composable
private fun ColorItem(
    name: String,
    color: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .height(48.dp)
                .background(color),
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 12.dp),
        )
    }
}
