package com.on.dialog.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.logo_dialog
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DefaultDialogTopBar(
    modifier: Modifier = Modifier,
) {
    DialogTopAppBar(
        modifier = modifier,
        title = "Dialog",
        centerAligned = false,
        navigationIcon = {
            Row {
                Spacer(modifier = Modifier.width(DialogTheme.spacing.medium))
                Image(
                    imageVector = vectorResource(Res.drawable.logo_dialog),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                )
            }
        },
    )
}

@Preview
@Composable
private fun DefaultDialogTopBarPreview() {
    DialogTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DefaultDialogTopBar()
        }
    }
}
