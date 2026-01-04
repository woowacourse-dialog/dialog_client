package com.on.dialog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InteractionIndicator(
    icon: ImageVector,
    count: Int,
    modifier: Modifier = Modifier,
    iconTint: Color = DialogTheme.colorScheme.primary,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = iconTint,
        )
        Text(
            text = if (count >= 1000) "${count / 1000}K+" else count.toString(),
            textAlign = TextAlign.Center,
            style = DialogTheme.typography.bodyMedium,
            modifier = Modifier.width(32.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InteractionIndicatorPreview() {
    DialogTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InteractionIndicator(icon = DialogIcons.Chat, count = 25)
            InteractionIndicator(icon = DialogIcons.Bookmark, count = 1000)
            InteractionIndicator(icon = DialogIcons.BookmarkBorder, count = 10)
            InteractionIndicator(icon = DialogIcons.Favorite, count = 3, iconTint = Color.Red)
            InteractionIndicator(icon = DialogIcons.FavoriteBorder, count = 5000)
            InteractionIndicator(icon = DialogIcons.Person, count = 4)
        }
    }
}
