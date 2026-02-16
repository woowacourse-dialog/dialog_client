package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
fun IconTextRow(
    iconImage: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalContentColor provides DialogTheme.colorScheme.onSurfaceVariant,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = iconImage,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(DialogTheme.spacing.small))
            Text(
                text = text,
                style = DialogTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IconTextRowPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        IconTextRow(iconImage = DialogIcons.Group, text = "1/4")
    }
}