package com.on.dialog.designsystem.component.snackbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme

@Immutable
data class DialogSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val state: SnackbarState = SnackbarState.DEFAULT,
) : SnackbarVisuals

@Composable
fun DialogSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
) {
    val visuals = snackbarData.visuals as? DialogSnackbarVisuals
    val state = visuals?.state ?: SnackbarState.DEFAULT

    val scheme = DialogTheme.colorScheme

    val containerColor = when (state) {
        SnackbarState.DEFAULT -> SnackbarDefaults.color
        SnackbarState.POSITIVE -> scheme.primary
        SnackbarState.NEGATIVE -> scheme.error
    }

    val contentColor = when (state) {
        SnackbarState.DEFAULT -> SnackbarDefaults.contentColor
        SnackbarState.POSITIVE -> scheme.onPrimary
        SnackbarState.NEGATIVE -> scheme.onError
    }

    val icon = when (state) {
        SnackbarState.DEFAULT -> Icons.Rounded.Info
        SnackbarState.POSITIVE -> Icons.Rounded.CheckCircle
        SnackbarState.NEGATIVE -> Icons.Rounded.Error
    }

    Snackbar(
        modifier = modifier.padding(12.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        action = {
            snackbarData.visuals.actionLabel?.let { label ->
                TextButton(
                    onClick = { snackbarData.performAction() },
                    colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
                ) {
                    Text(text = label)
                }
            }
        },
        dismissAction = {
            if (snackbarData.visuals.withDismissAction) {
                IconButton(onClick = { snackbarData.dismiss() }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "닫기",
                        tint = contentColor.copy(alpha = 0.8f),
                    )
                }
            }
        },
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Text(text = snackbarData.visuals.message)
            }
        },
    )
}

@Preview
@Composable
private fun DialogSnackbarPreview() {
    DialogTheme {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SnackbarState.entries.forEach { state ->
                val visuals = DialogSnackbarVisuals(
                    message = "테스트 메시지",
                    state = state,
                )
                DialogSnackbar(
                    snackbarData = object : SnackbarData {
                        override val visuals: SnackbarVisuals = visuals

                        override fun dismiss() {}

                        override fun performAction() {}
                    },
                )
            }
        }
    }
}
