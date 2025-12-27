package com.on.dialog.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Immutable
data class Spacing(
    val none: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,
    val extraExtraLarge: Dp = 32.dp,
    val huge: Dp = 48.dp,
)

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }

@Preview(name = "Spacing Tokens", heightDp = 800)
@Composable
private fun SpacingPreview() {
    DialogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Spacing Tokens",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                SpacingItem("none", DialogTheme.spacing.none)
                SpacingItem("extraSmall", DialogTheme.spacing.extraSmall)
                SpacingItem("small", DialogTheme.spacing.small)
                SpacingItem("medium", DialogTheme.spacing.medium)
                SpacingItem("large", DialogTheme.spacing.large)
                SpacingItem("extraLarge", DialogTheme.spacing.extraLarge)
                SpacingItem("extraExtraLarge", DialogTheme.spacing.extraExtraLarge)
                SpacingItem("huge", DialogTheme.spacing.huge)
            }
        }
    }
}

@Composable
private fun SpacingItem(
    name: String,
    value: Dp,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "$name ($value)",
            style = MaterialTheme.typography.bodyMedium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(value)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.outline),
            )
        }
    }
}
