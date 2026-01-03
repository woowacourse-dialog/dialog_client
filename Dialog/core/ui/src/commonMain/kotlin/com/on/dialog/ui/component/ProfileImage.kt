package com.on.dialog.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    crossfade: Boolean = true,
) {
    AsyncImage(
        model =
            ImageRequest
                .Builder(LocalPlatformContext.current)
                .data(imageUrl)
                .crossfade(crossfade)
                .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .border(width = 0.5.dp, color = DialogTheme.colorScheme.onSurface, shape = CircleShape)
            .clip(CircleShape),
    )
}

@Preview
@Composable
private fun ProfileImagePreview() {
    DialogTheme {
        ProfileImage(
            imageUrl = "",
            contentDescription = "",
            modifier = Modifier.size(32.dp),
        )
    }
}
