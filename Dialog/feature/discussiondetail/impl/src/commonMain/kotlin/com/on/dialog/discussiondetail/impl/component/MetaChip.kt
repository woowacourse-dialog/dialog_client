package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
internal fun MetaChip(
    text: String,
    modifier: Modifier = Modifier,
    iconImage: ImageVector? = null,
) {
    Surface(
        modifier = modifier,
        shape = DialogTheme.shapes.small,
        color = DialogTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
            modifier = Modifier.padding(
                horizontal = DialogTheme.spacing.small,
                vertical = DialogTheme.spacing.extraSmall,
            ),
        ) {
            iconImage?.let { imageVector ->
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }
            Text(
                text = text,
                style = DialogTheme.typography.labelMedium,
                color = DialogTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@ThemePreview
@Composable
private fun MetaChipPreview() {
    DialogTheme {
        MetaChip(
            text = "굿샷",
            iconImage = DialogIcons.Place,
            modifier = Modifier.padding(4.dp),
        )
    }
}
