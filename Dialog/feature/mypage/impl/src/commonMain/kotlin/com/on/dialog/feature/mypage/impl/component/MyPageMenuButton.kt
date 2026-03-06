package com.on.dialog.feature.mypage.impl.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme

@Composable
internal fun MyPageMenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = DialogTheme.shapes.small)
            .clickable { onClick() }
            .padding(all = DialogTheme.spacing.medium),
    ) {
        leadingIcon?.let {
            Box(modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
                leadingIcon()
            }
            Spacer(modifier = Modifier.width(width = ButtonDefaults.IconSpacing))
        }
        Text(text = text, style = DialogTheme.typography.labelLarge)
    }
}

@Preview
@Composable
private fun MyPageMenuButtonPreview() {
    DialogTheme {
        Surface {
            MyPageMenuButton(
                text = "내가 개설한 토론 보기",
                leadingIcon = { Icon(imageVector = DialogIcons.Forum, contentDescription = null) },
                onClick = {},
            )
        }
    }
}
