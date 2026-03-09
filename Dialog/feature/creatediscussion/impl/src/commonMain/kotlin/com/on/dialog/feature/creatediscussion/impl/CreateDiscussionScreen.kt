package com.on.dialog.feature.creatediscussion.impl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogDatePicker
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogIconButtonTone
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.component.DialogTimePicker
import com.on.dialog.designsystem.component.DialogToggle
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.creatediscussion.impl.component.verticalScrollbar
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionEffect
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionIntent
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionState
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionViewModel
import com.on.dialog.feature.creatediscussion.impl.viewmodel.DiscussionMode
import com.on.dialog.ui.component.markdown.DialogMarkdown
import com.on.dialog.ui.component.markdown.MarkdownEditor
import dialog.feature.creatediscussion.impl.generated.resources.Res
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_button_cancel
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_button_submit
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_button_submit_loading
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_end_time_range
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_past_date
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_start_time_range
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_content
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_date
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_end_date
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_end_time
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_meetup
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_participant_count
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_place
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_start_time
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_title
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_label_track
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_content
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_date
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_end_time
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_place
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_start_time
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_title
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_placeholder_track
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_title
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_title_count_format
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CreateDiscussionScreen(
    goBack: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateDiscussionViewModel = koinViewModel(),
) {
    val uiState: CreateDiscussionState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = LocalSnackbarDelegate.current
    var showMarkdownEditor by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CreateDiscussionEffect.ShowSnackbar -> snackbarState.showSnackbar(
                    message = getString(effect.message),
                    state = effect.state,
                )

                is CreateDiscussionEffect.NavigateToDetail -> navigateToDetail(effect.discussionId)
            }
        }
    }

    CreateDiscussionScreen(
        uiState = uiState,
        onBackClick = goBack,
        onContentClick = { showMarkdownEditor = true },
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )

    if (showMarkdownEditor) {
        MarkdownEditor(
            initialContent = uiState.content,
            onConfirm = { newContent: String ->
                showMarkdownEditor = false
                viewModel.onIntent(CreateDiscussionIntent.OnContentChange(newContent))
            },
            onExit = { showMarkdownEditor = false },
        )
    }
}

@Composable
private fun CreateDiscussionScreen(
    uiState: CreateDiscussionState,
    onBackClick: () -> Unit,
    onContentClick: () -> Unit,
    onIntent: (CreateDiscussionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = stringResource(Res.string.create_discussion_title),
            navigationIcon = {
                DialogIconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = DialogIcons.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
            centerAligned = false,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            TitleField(
                title = uiState.title,
                onTitleChange = { onIntent(CreateDiscussionIntent.OnTitleChange(it)) },
                label = stringResource(Res.string.create_discussion_label_title),
                placeHolder = stringResource(Res.string.create_discussion_placeholder_title),
            )

            DialogDropdownMenu(
                options = uiState.trackOptions.map { stringResource(it) }.toImmutableList(),
                selectedIndex = uiState.selectedTrackIndex.takeIf { it >= 0 },
                onSelectedIndexChange = { onIntent(CreateDiscussionIntent.OnTrackIndexChange(it)) },
                label = stringResource(Res.string.create_discussion_label_track),
                placeholder = stringResource(Res.string.create_discussion_placeholder_track),
            )

            Spacer(modifier = Modifier.height(20.dp))

            DialogToggle(
                checked = uiState.mode is DiscussionMode.Offline,
                onCheckedChange = { onIntent(CreateDiscussionIntent.OnMeetupEnabledChange(it)) },
                label = stringResource(Res.string.create_discussion_label_meetup),
                modifier = Modifier.padding(start = DialogTheme.spacing.small),
            )

            AnimatedVisibility(
                visible = uiState.mode is DiscussionMode.Offline,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                val offlineMode = uiState.mode as? DiscussionMode.Offline
                if (offlineMode != null) Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    OfflineDiscussion(
                        place = offlineMode.place,
                        onPlaceChange = { onIntent(CreateDiscussionIntent.OnPlaceChange(it)) },
                        participantCount = offlineMode.participantCount,
                        onParticipantCountChange = {
                            onIntent(CreateDiscussionIntent.OnParticipantCountChange(it))
                        },
                        selectedDate = offlineMode.selectedDate,
                        onDateSelected = { onIntent(CreateDiscussionIntent.OnDateChange(it)) },
                        selectedStartTime = offlineMode.selectedStartTime,
                        onStartTimeSelected = {
                            onIntent(CreateDiscussionIntent.OnStartTimeChange(it))
                        },
                        selectedEndTime = offlineMode.selectedEndTime,
                        onEndTimeSelected = { onIntent(CreateDiscussionIntent.OnEndTimeChange(it)) },
                        selectedDateErrorMessage = offlineMode.selectedDateErrorMessage
                            ?.let { stringResource(it) },
                        selectedStartTimeErrorMessage = offlineMode.selectedStartTimeErrorMessage
                            ?.let { stringResource(it) },
                        selectedEndTimeErrorMessage = offlineMode.selectedEndTimeErrorMessage
                            ?.let { stringResource(it) },
                    )
                }
            }

            AnimatedVisibility(
                visible = uiState.mode is DiscussionMode.Online,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                val onlineMode = uiState.mode as? DiscussionMode.Online
                if (onlineMode != null) Column {
                    val endDateOptions =
                        onlineMode.endDateOptions.map { stringResource(it) }.toImmutableList()
                    Spacer(modifier = Modifier.height(20.dp))
                    DialogDropdownMenu(
                        options = endDateOptions,
                        selectedIndex = onlineMode.selectedEndDateIndex.takeIf { it >= 0 },
                        onSelectedIndexChange = {
                            onIntent(CreateDiscussionIntent.OnEndDateIndexChange(it))
                        },
                        label = stringResource(Res.string.create_discussion_label_end_date),
                        placeholder = stringResource(Res.string.create_discussion_placeholder_date),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            DiscussionContent(
                content = uiState.content,
                onContentChange = onContentClick,
            )

        }
        SubmitButtonRow(
            isSubmitting = uiState.isSubmitting,
            isSubmitEnabled = uiState.isSubmitEnabled,
            onBackClick = onBackClick,
            onSubmitClick = { onIntent(CreateDiscussionIntent.OnSubmitClick) },
        )
    }
}

@Composable
private fun SubmitButtonRow(
    isSubmitting: Boolean,
    isSubmitEnabled: Boolean,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .imePadding(),
        horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraExtraLarge)
    ) {
        DialogButton(
            text = stringResource(Res.string.create_discussion_button_cancel),
            style = DialogButtonStyle.Secondary,
            onClick = onBackClick,
            modifier = Modifier.weight(1f),
        )
        DialogButton(
            text = if (isSubmitting) stringResource(Res.string.create_discussion_button_submit_loading)
            else stringResource(Res.string.create_discussion_button_submit),
            onClick = onSubmitClick,
            modifier = Modifier.weight(1f),
            enabled = isSubmitEnabled && !isSubmitting,
        )
    }
}

@Composable
fun DiscussionContent(
    content: String,
    onContentChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Text(
        text = stringResource(Res.string.create_discussion_label_content),
        color = DialogTheme.colorScheme.primary,
        style = DialogTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(DialogTheme.spacing.small),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(DialogTheme.shapes.small)
            .background(color = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3F))
            .clickable(onClick = { onContentChange() })
            .padding(all = DialogTheme.spacing.large)
            .verticalScrollbar(scrollState)
    ) {
        if (content.isEmpty()) {
            Text(
                text = stringResource(Res.string.create_discussion_placeholder_content),
                style = DialogTheme.typography.bodyLarge,
                color = DialogTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            DialogMarkdown(
                content = content,
                modifier = Modifier.verticalScroll(scrollState),
            )
        }
    }
}

@Composable
private fun OfflineDiscussion(
    place: String,
    onPlaceChange: (String) -> Unit,
    participantCount: Int,
    onParticipantCountChange: (Int) -> Unit,
    selectedDate: LocalDate?,
    selectedDateErrorMessage: String?,
    onDateSelected: (LocalDate) -> Unit,
    selectedStartTime: LocalTime?,
    selectedStartTimeErrorMessage: String?,
    onStartTimeSelected: (LocalTime) -> Unit,
    selectedEndTime: LocalTime?,
    selectedEndTimeErrorMessage: String?,
    onEndTimeSelected: (LocalTime) -> Unit,
) {
    Column {
        TitleField(
            title = place,
            onTitleChange = onPlaceChange,
            label = stringResource(Res.string.create_discussion_label_place),
            placeHolder = stringResource(Res.string.create_discussion_placeholder_place),
        )

        ParticipantContent(
            label = stringResource(Res.string.create_discussion_label_participant_count),
            participantCount = participantCount,
            onParticipantCountChange = onParticipantCountChange,
        )

        Spacer(modifier = Modifier.height(20.dp))

        DialogDatePicker(
            label = stringResource(Res.string.create_discussion_label_date),
            placeholder = stringResource(Res.string.create_discussion_placeholder_date),
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            isError = true,
            supportingText = selectedDateErrorMessage,
        )

        DialogTimePicker(
            selectedTime = selectedStartTime,
            onTimeSelected = onStartTimeSelected,
            placeholder = stringResource(Res.string.create_discussion_placeholder_start_time),
            label = stringResource(Res.string.create_discussion_label_start_time),
            isError = true,
            supportingText = selectedStartTimeErrorMessage,
        )

        DialogTimePicker(
            selectedTime = selectedEndTime,
            onTimeSelected = onEndTimeSelected,
            placeholder = stringResource(Res.string.create_discussion_placeholder_end_time),
            label = stringResource(Res.string.create_discussion_label_end_time),
            isError = true,
            supportingText = selectedEndTimeErrorMessage,
        )
    }
}

@Composable
private fun ParticipantContent(
    label: String,
    participantCount: Int,
    onParticipantCountChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = DialogTheme.colorScheme.primary,
            style = DialogTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(DialogTheme.spacing.small),
        )

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DialogIconButton(
                onClick = { onParticipantCountChange(participantCount - 1) },
                tone = DialogIconButtonTone.Primary,
            ) {
                Icon(DialogIcons.Minus, null)
            }

            Text(
                text = participantCount.toString(),
                modifier = Modifier.padding(horizontal = DialogTheme.spacing.medium)
            )

            DialogIconButton(
                onClick = { onParticipantCountChange(participantCount + 1) },
                tone = DialogIconButtonTone.Primary,
            ) {
                Icon(DialogIcons.Plus, null)
            }
        }
    }
}

@Composable
private fun TitleField(
    title: String,
    onTitleChange: (String) -> Unit,
    label: String,
    placeHolder: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            DialogTextField(
                value = title,
                onValueChange = onTitleChange,
                label = label,
                modifier = Modifier.fillMaxWidth(),
                placeholder = placeHolder,
                singleLine = true,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = stringResource(
                    Res.string.create_discussion_title_count_format,
                    title.length,
                    50,
                ),
                color = DialogTheme.colorScheme.primary,
                fontSize = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateDiscussionScreenPreview() {
    DialogTheme {
        CreateDiscussionScreen(
            uiState = CreateDiscussionState(),
            onBackClick = {},
            onContentClick = {},
            onIntent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateDiscussionScreenPreview2() {
    DialogTheme {
        CreateDiscussionScreen(
            uiState = CreateDiscussionState(
                mode = DiscussionMode.Offline(
                    selectedDateErrorMessage = Res.string.create_discussion_error_past_date,
                    selectedStartTimeErrorMessage = Res.string.create_discussion_error_start_time_range,
                    selectedEndTimeErrorMessage = Res.string.create_discussion_error_end_time_range,
                ),
            ),
            onBackClick = {},
            onContentClick = {},
            onIntent = {},
        )
    }
}
