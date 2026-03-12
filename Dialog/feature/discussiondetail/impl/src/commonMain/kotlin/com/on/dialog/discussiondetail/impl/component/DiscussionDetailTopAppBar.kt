package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.action_delete
import dialog.feature.discussiondetail.impl.generated.resources.action_edit
import dialog.feature.discussiondetail.impl.generated.resources.action_more
import dialog.feature.discussiondetail.impl.generated.resources.action_report
import dialog.feature.discussiondetail.impl.generated.resources.header_bookmark_content_description
import dialog.feature.discussiondetail.impl.generated.resources.header_like_content_description
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionDetailTopAppBar(
    isMyDiscussion: Boolean,
    isLiked: Boolean,
    isBookmarked: Boolean,
    goBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReportClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onLikeClick: () -> Unit,
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
            DiscussionDetailDefaultActions(
                isBookmarked = isBookmarked,
                onBookmarkClick = onBookmarkClick,
                isLiked = isLiked,
                onLikeClick = onLikeClick,
            )
            DiscussionDetailActions(
                isMyDiscussion = isMyDiscussion,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                onReportClick = onReportClick,
            )
        },
    )
}

@Composable
private fun DiscussionDetailDefaultActions(
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
) {
    DialogIconButton(onClick = onBookmarkClick) {
        Icon(
            imageVector = if (isBookmarked) DialogIcons.Bookmark else DialogIcons.BookmarkBorder,
            contentDescription = stringResource(Res.string.header_bookmark_content_description),
        )
    }
    DialogIconButton(onClick = onLikeClick) {
        Icon(
            imageVector = if (isLiked) DialogIcons.Favorite else DialogIcons.FavoriteBorder,
            contentDescription = stringResource(Res.string.header_like_content_description),
        )
    }
}

@Composable
private fun DiscussionDetailActions(
    isMyDiscussion: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReportClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier) {
        DialogIconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = DialogIcons.Menu,
                contentDescription = stringResource(Res.string.action_more),
            )
        }

        when (isMyDiscussion) {
            true -> {
                MineDetailDropDownMenu(
                    showMenu = showMenu,
                    onDismiss = { showMenu = false },
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

            false -> {
                NotMineDetailDropDownMenu(
                    showMenu = showMenu,
                    onDismiss = { showMenu = false },
                    onReportClick = {
                        onReportClick()
                        showMenu = false
                    },
                )
            }
        }
    }
}

@Composable
private fun NotMineDetailDropDownMenu(
    showMenu: Boolean,
    onDismiss: () -> Unit,
    onReportClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DetailDropDownMenu(
        modifier = modifier,
        showMenu = showMenu,
        onDismissRequest = onDismiss,
    ) {
        DetailDropDownMenuItem(
            text = stringResource(Res.string.action_report),
            onClick = onReportClick,
        )
    }
}

@Composable
private fun MineDetailDropDownMenu(
    showMenu: Boolean,
    onDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DetailDropDownMenu(
        modifier = modifier,
        showMenu = showMenu,
        onDismissRequest = onDismiss,
    ) {
        DetailDropDownMenuItem(
            text = stringResource(Res.string.action_edit),
            onClick = onEditClick,
        )
        DetailDropDownMenuItem(
            text = stringResource(Res.string.action_delete),
            onClick = onDeleteClick,
        )
    }
}

@Composable
private fun DetailDropDownMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        modifier = modifier.clip(DialogTheme.shapes.small),
        text = { Text(text = text) },
        onClick = onClick,
    )
}

@Composable
private fun DetailDropDownMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        shape = DialogTheme.shapes.medium,
        modifier = modifier.padding(horizontal = DialogTheme.spacing.extraSmall),
    ) {
        ProvideTextStyle(
            value = DialogTheme.typography.labelLarge,
            content = content,
        )
    }
}

@ThemePreview
@Composable
private fun NotMineDetailDropDownMenuPreview() {
    DialogTheme {
        Surface {
            NotMineDetailDropDownMenu(
                showMenu = true,
                onDismiss = {},
                onReportClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun MineDetailDropDownMenuPreview() {
    DialogTheme {
        Surface {
            MineDetailDropDownMenu(
                showMenu = true,
                onDismiss = {},
                onEditClick = {},
                onDeleteClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionDetailTopAppBarPreview() {
    DialogTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(DialogTheme.colorScheme.background)
                    .padding(vertical = 16.dp),
            ) {
                DiscussionDetailTopAppBar(
                    isMyDiscussion = true,
                    goBack = {},
                    onEditClick = {},
                    onDeleteClick = {},
                    onReportClick = {},
                    onBookmarkClick = {},
                    onLikeClick = {},
                    isLiked = true,
                    isBookmarked = true,
                )

                DiscussionDetailTopAppBar(
                    isMyDiscussion = false,
                    goBack = {},
                    onEditClick = {},
                    onDeleteClick = {},
                    onReportClick = {},
                    onBookmarkClick = {},
                    onLikeClick = {},
                    isLiked = true,
                    isBookmarked = true,
                )
            }
        }
    }
}
