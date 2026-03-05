package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.action_more
import dialog.feature.discussiondetail.impl.generated.resources.action_delete
import dialog.feature.discussiondetail.impl.generated.resources.action_edit
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionDetailTopAppBar(
    showActions: Boolean,
    goBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogTopAppBar(
        modifier = modifier,
        title = "",
        navigationIcon = {
            DialogIconButton(onClick = goBack) {
                Icon(imageVector = DialogIcons.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            if (showActions) {
                DiscussionDetailActions(
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                )
            }
        },
    )
}

@Composable
private fun DiscussionDetailActions(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    Box {
        DialogIconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = DialogIcons.MoreVert,
                contentDescription = stringResource(Res.string.action_more),
            )
        }

        DetailDropDownMenu(
            showMenu = showMenu,
            onDismissRequest = { showMenu = false },
            onEditClick = {
                onEditClick()
                showMenu = false
            },
            onDeleteClick = {
                onDeleteClick()
                showMenu = false
            },
        )
    }
}

@Composable
private fun DetailDropDownMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        val textStyle = DialogTheme.typography.titleMedium
        DropdownMenuItem(
            text = { Text(text = stringResource(Res.string.action_edit), style = textStyle) },
            colors = dropdownColors(),
            onClick = onEditClick,
            leadingIcon = { Icon(imageVector = DialogIcons.Edit, contentDescription = null) },
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(Res.string.action_delete), style = textStyle) },
            colors = dropdownColors(),
            onClick = onDeleteClick,
            leadingIcon = { Icon(imageVector = DialogIcons.Delete, contentDescription = null) },
        )
    }
}

@Composable
private fun dropdownColors(): MenuItemColors = MenuDefaults.itemColors(
    textColor = DialogTheme.colorScheme.onSurface,
    leadingIconColor = DialogTheme.colorScheme.onSurface,
)

@ThemePreview
@Composable
private fun DiscussionDetailActionsPreview() {
    DialogTheme {
        Surface {
            DetailDropDownMenu(
                showMenu = true,
                onDismissRequest = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
fun DiscussionDetailTopAppBarPreview() {
    DialogTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(DialogTheme.colorScheme.background)
                .padding(vertical = 16.dp),
        ) {
            DiscussionDetailTopAppBar(
                showActions = true,
                goBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )

            DiscussionDetailTopAppBar(
                showActions = false,
                goBack = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }
    }
}
