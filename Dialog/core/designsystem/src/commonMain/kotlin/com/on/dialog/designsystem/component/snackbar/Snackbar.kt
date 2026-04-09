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
import androidx.compose.material3.ColorScheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.designsystem.generated.resources.Res
import dialog.core.designsystem.generated.resources.dialog_snackbar_dismiss_action_icon_content_description
import org.jetbrains.compose.resources.stringResource

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

    val containerColor = state.containerColor()
    val contentColor = state.contentColor()

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
                        contentDescription = stringResource(Res.string.dialog_snackbar_dismiss_action_icon_content_description),
                        tint = contentColor.copy(alpha = 0.8f),
                    )
                }
            }
        },
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
            ) {
                Icon(
                    imageVector = state.leadingIcon(),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Text(text = snackbarData.visuals.message, modifier = Modifier.weight(1f))
            }
        },
    )
}

@Composable
private fun SnackbarState.containerColor(scheme: ColorScheme = DialogTheme.colorScheme): Color =
    when (this) {
        SnackbarState.DEFAULT -> SnackbarDefaults.color
        SnackbarState.POSITIVE -> scheme.primary
        SnackbarState.NEGATIVE -> scheme.error
    }

@Composable
private fun SnackbarState.contentColor(scheme: ColorScheme = DialogTheme.colorScheme): Color =
    when (this) {
        SnackbarState.DEFAULT -> SnackbarDefaults.contentColor
        SnackbarState.POSITIVE -> scheme.onPrimary
        SnackbarState.NEGATIVE -> scheme.onError
    }

@Composable
private fun SnackbarState.leadingIcon(): ImageVector = when (this) {
    SnackbarState.DEFAULT -> Icons.Rounded.Info
    SnackbarState.POSITIVE -> Icons.Rounded.CheckCircle
    SnackbarState.NEGATIVE -> Icons.Rounded.Error
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
                    message = "테스트",
                    state = state,
                    actionLabel = "되돌리기",
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
