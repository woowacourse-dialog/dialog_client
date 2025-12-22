package com.on.dialog.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape: Shape = RoundedCornerShape(DialogButtonDefaults.Default.cornerRadius),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        contentPadding = contentPadding,
        content = content,
        shape = shape,
    )
}

@Composable
fun DialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    DialogButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding =
            if (leadingIcon != null) {
                ButtonDefaults.ButtonWithIconContentPadding
            } else {
                ButtonDefaults.ContentPadding
            },
    ) {
        DialogButtonContext(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
fun DialogOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape: Shape = RoundedCornerShape(DialogButtonDefaults.Default.cornerRadius),
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
            ),
        border =
            BorderStroke(
                width = DialogButtonDefaults.Default.outlinedButtonBorderWidth,
                color =
                    if (enabled) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = DialogButtonDefaults.Default.disabledOutlinedButtonBorderAlpha,
                        )
                    },
            ),
        contentPadding = contentPadding,
        shape = shape,
        content = content,
    )
}

@Composable
fun DialogOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    DialogOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding =
            if (leadingIcon != null) {
                ButtonDefaults.ButtonWithIconContentPadding
            } else {
                ButtonDefaults.ContentPadding
            },
    ) {
        DialogButtonContext(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
private fun DialogButtonContext(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start =
                    if (leadingIcon != null) {
                        ButtonDefaults.IconSpacing
                    } else {
                        0.dp
                    },
            ),
    ) {
        text()
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogButtonPreview() {
    DialogTheme {
        DialogButton(onClick = {}, text = { Text("다이얼로그") })
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogOutlinedButtonPreview() {
    DialogTheme {
        DialogOutlinedButton(onClick = {}, text = { Text("로그인") })
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogButtonLeadingIconPreview() {
    DialogTheme {
        DialogButton(
            onClick = {},
            text = { Text("추가") },
            leadingIcon = {
                Icon(imageVector = DialogIcons.Add, contentDescription = null)
            },
        )
    }
}

data class DialogButtonDefaults(
    val disabledOutlinedButtonBorderAlpha: Float,
    val cornerRadius: Dp,
    val outlinedButtonBorderWidth: Dp,
) {
    companion object {
        val Default = DialogButtonDefaults(
            disabledOutlinedButtonBorderAlpha = 0.12f,
            cornerRadius = 8.dp,
            outlinedButtonBorderWidth = 1.dp,
        )
    }
}
