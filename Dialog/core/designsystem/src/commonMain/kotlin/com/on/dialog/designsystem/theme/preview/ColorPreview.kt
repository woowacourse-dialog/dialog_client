package com.on.dialog.designsystem.theme.preview

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
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "Light Colors", heightDp = 2000)
@Composable
internal fun ColorPalettePreview() {
    DialogTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ColorPaletteContent()
        }
    }
}

@Preview(name = "Dark Colors", heightDp = 2000)
@Composable
internal fun ColorPaletteDarkPreview() {
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
