package com.on.dialog.designsystem.theme.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.ShadowLevel
import com.on.dialog.designsystem.theme.dropShadow
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "Shadow Tokens", heightDp = 800)
@Composable
internal fun ShadowPreview() {
    DialogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Text(
                    text = "Shadow Tokens",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                ShadowItem("NONE", ShadowLevel.NONE)
                ShadowItem("SMALL", ShadowLevel.SMALL)
                ShadowItem("MEDIUM", ShadowLevel.MEDIUM)
                ShadowItem("LARGE", ShadowLevel.LARGE)
                ShadowItem("EXTRA_LARGE", ShadowLevel.EXTRA_LARGE)
            }
        }
    }
}

@Composable
private fun ShadowItem(
    name: String,
    level: ShadowLevel,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .dropShadow(level = level, shape = MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
