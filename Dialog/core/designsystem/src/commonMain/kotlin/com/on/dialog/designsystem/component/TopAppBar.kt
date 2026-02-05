package com.on.dialog.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.dropShadow

/**
 * 상단에서 제목과 내비게이션, 액션을 제공하는 TopAppBar 컴포넌트입니다.
 *
 * @param title 가운데(혹은 왼쪽)에 보여줄 제목 텍스트.
 * @param modifier 앱바에 적용할 Modifier.
 * @param navigationIcon 좌측 내비게이션 아이콘 슬롯.
 * @param actions 우측 액션 슬롯.
 * @param centerAligned true면 CenterAlignedTopAppBar를 사용합니다.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    centerAligned: Boolean = true,
) {
    val titleContent = @Composable { Text(text = title) }

    if (centerAligned) {
        CenterAlignedTopAppBar(
            modifier = modifier.dropShadow(RectangleShape),
            title = titleContent,
            expandedHeight = 52.dp,
            navigationIcon = navigationIcon ?: {},
            actions = actions,
            windowInsets = WindowInsets(0, 0, 0, 0),
        )
    } else {
        TopAppBar(
            modifier = modifier.dropShadow(RectangleShape),
            title = titleContent,
            expandedHeight = 52.dp,
            navigationIcon = navigationIcon ?: {},
            actions = actions,
            windowInsets = WindowInsets(0, 0, 0, 0),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogTopAppBarPreviewLight() {
    DialogTheme {
        DialogTopAppBarPreviewContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogTopAppBarPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogTopAppBarPreviewContent()
    }
}

@Composable
private fun DialogTopAppBarPreviewContent() {
    Column {
        DialogTopAppBar(
            title = "Dialog",
            navigationIcon = {
                DialogIconButton(onClick = {}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                DialogIconButton(onClick = {}) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },
        )

        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.Gray),
        )

        DialogTopAppBar(
            title = "Dialog",
            centerAligned = false,
            navigationIcon = {
                DialogIconButton(onClick = {}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
        )
    }
}
