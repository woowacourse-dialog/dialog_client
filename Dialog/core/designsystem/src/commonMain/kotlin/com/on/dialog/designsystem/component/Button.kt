package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.Gray400

enum class DialogButtonStyle { Primary, Secondary, None }

@Immutable
sealed interface DialogButtonContent {

    data class Text(
        val text: String,
    ) : DialogButtonContent

    data class Icon(
        val icon: @Composable (Modifier) -> Unit,
        val size: Dp = 50.dp,
    ) : DialogButtonContent

    data class TextWithIcon(
        val text: String,
        val icon: @Composable (Modifier) -> Unit,
    ) : DialogButtonContent
}

@Composable
fun DialogButton(
    onClick: () -> Unit,
    content: DialogButtonContent,
    modifier: Modifier = Modifier,
    style: DialogButtonStyle = DialogButtonStyle.Primary,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.let {
            if (content is DialogButtonContent.Icon) {
                it.size(content.size)
            } else {
                it
            }
        },
        colors = buttonColorsByStyle(style),
        shape = DialogTheme.shapes.small,
        contentPadding = when (content) {
            is DialogButtonContent.Icon -> PaddingValues(0.dp)
            is DialogButtonContent.Text -> ButtonDefaults.ContentPadding
            is DialogButtonContent.TextWithIcon ->
                ButtonDefaults.ButtonWithIconContentPadding
        }
    ) {
        when (content) {
            is DialogButtonContent.Icon -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content.icon(
                        Modifier.size(content.size * 0.6f)
                    )
                }
            }

            is DialogButtonContent.Text -> {
                Text(text = content.text)
            }

            is DialogButtonContent.TextWithIcon -> {
                DialogButtonContent(
                    text = content.text,
                    icon = content.icon,
                )
            }
        }
    }
}

@Composable
private fun DialogButtonContent(
    text: String,
    icon: @Composable (Modifier) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)
        ) {
            icon(Modifier.size(ButtonDefaults.IconSize))
        }
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = text)
    }
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
    Column(
        modifier = Modifier.padding(horizontal = 50.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DialogButton(
            onClick = {},
            content = DialogButtonContent.Text("Primary")
        )

        DialogButton(
            onClick = {},
            style = DialogButtonStyle.Secondary,
            content = DialogButtonContent.Text("Secondary")
        )

        DialogButton(
            onClick = {},
            style = DialogButtonStyle.None,
            content = DialogButtonContent.Text("None")
        )

        DialogButton(
            onClick = {},
            content = DialogButtonContent.Icon(
                icon = { modifier ->
                    Icon(
                        modifier = modifier,
                        imageVector = DialogIcons.Add,
                        contentDescription = null
                    )
                },
                size = 50.dp
            )
        )

        DialogButton(
            onClick = {},
            content = DialogButtonContent.Icon(
                icon = { modifier ->
                    Icon(
                        modifier = modifier,
                        imageVector = DialogIcons.Add,
                        contentDescription = null
                    )
                },
                size = 100.dp
            )
        )

        DialogButton(
            onClick = {},
            content = DialogButtonContent.TextWithIcon(
                text = "더하기",
                icon = { modifier ->
                    Icon(
                        modifier = modifier,
                        imageVector = DialogIcons.Add,
                        contentDescription = null
                    )
                }
            )
        )
    }
}

