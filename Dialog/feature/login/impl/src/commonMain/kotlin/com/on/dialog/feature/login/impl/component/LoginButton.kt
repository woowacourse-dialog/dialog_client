package com.on.dialog.feature.login.impl.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.dropShadow
import dialog.feature.login.impl.generated.resources.Res
import dialog.feature.login.impl.generated.resources.ic_apple
import dialog.feature.login.impl.generated.resources.ic_github
import dialog.feature.login.impl.generated.resources.login_with_apple
import dialog.feature.login.impl.generated.resources.login_with_github
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AppleLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SocialLoginButton(
        text = stringResource(Res.string.login_with_apple),
        iconRes = Res.drawable.ic_apple,
        containerColor = Color.Black,
        contentColor = Color.White,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
internal fun GithubLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SocialLoginButton(
        text = stringResource(Res.string.login_with_github),
        iconRes = Res.drawable.ic_github,
        containerColor = Color(0xFF24292F),
        contentColor = Color.White,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun SocialLoginButton(
    text: String,
    iconRes: DrawableResource,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .dropShadow(shape = DialogTheme.shapes.medium),
        shape = DialogTheme.shapes.medium,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = DialogTheme.spacing.mediumLarge),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(resource = iconRes),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(20.dp),
            )
            Text(
                text = text,
                color = contentColor,
                style = DialogTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
            )
        }
    }
}

@ThemePreview
@Composable
private fun LoginButtonPreview() {
    DialogTheme {
        Column(
            modifier = Modifier.padding(DialogTheme.spacing.extraLarge),
        ) {
            AppleLoginButton(onClick = {})
            Spacer(modifier = Modifier.height(height = DialogTheme.spacing.medium))
            GithubLoginButton(onClick = {})
        }
    }
}
