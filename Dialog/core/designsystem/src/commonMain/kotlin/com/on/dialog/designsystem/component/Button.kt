package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.Gray400
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class DialogButtonStyle { Primary, Secondary, None }

@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: DialogButtonStyle = DialogButtonStyle.Primary,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColorsByStyle(style),
        shape = DialogTheme.shapes.small,
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
    text: String,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    leadingIcon?.let {
        Box(modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
    }
    Text(text = text)
}

@Composable
private fun buttonColorsByStyle(style: DialogButtonStyle) =
    when (style) {
        DialogButtonStyle.Primary -> {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            )
        }

        DialogButtonStyle.Secondary -> {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            )
        }

        DialogButtonStyle.None -> {
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                contentColor = DialogTheme.colorScheme.primary,
                disabledContentColor = Gray400,
            )
        }
    }

@Preview(showBackground = true)
@Composable
private fun DialogButtonPreviewLight() {
    DialogTheme {
        DialogButtonPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogButtonPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogButtonPreviewContent()
    }
}

@Composable
private fun DialogButtonPreviewContent() {
    Column {
        DialogButton(onClick = {}, text = "Primary")
        DialogButton(
            onClick = {},
            text = "Secondary",
            style = DialogButtonStyle.Secondary,
        )
        DialogButton(onClick = {}, text = "None", style = DialogButtonStyle.None)
        DialogButton(
            onClick = {},
            text = "Icon",
            leadingIcon = {
                Icon(imageVector = DialogIcons.Add, contentDescription = null)
            },
        )
    }
}
