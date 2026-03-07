package com.on.dialog.feature.creatediscussion.impl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionIntent
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionState
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CreateDiscussionScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateDiscussionViewModel = koinViewModel(),
) {
    val uiState: CreateDiscussionState by viewModel.uiState.collectAsStateWithLifecycle()
    var isMeetable by rememberSaveable { mutableStateOf(false) }

    CreateDiscussionScreen(
        uiState = uiState,
        onBackClick = goBack,
        isMeetable = isMeetable,
        isMeetableChanged = { isMeetable = it },
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}

@Composable
private fun CreateDiscussionScreen(
    uiState: CreateDiscussionState,
    onBackClick: () -> Unit,
    isMeetable: Boolean,
    isMeetableChanged: (Boolean) -> Unit,
    onIntent: (CreateDiscussionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = "토론 작성",
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            TitleField(
                title = uiState.title,
                onTitleChange = { onIntent(CreateDiscussionIntent.OnTitleChange(it)) },
                label = "제목",
                placeHolder = "제목에 핵심 내용을 요약합니다.",
            )

            DialogDropdownMenu(
                options = uiState.trackOptions.toImmutableList(),
                selectedIndex = uiState.selectedTrackIndex.takeIf { it >= 0 },
                onSelectedIndexChange = { onIntent(CreateDiscussionIntent.OnTrackIndexChange(it)) },
                label = "트랙",
                placeholder = "트랙을 선택해주세요",
            )

            Spacer(modifier = Modifier.height(20.dp))

            DialogToggle(
                checked = isMeetable,
                onCheckedChange = isMeetableChanged,
                label = "만나서 토론하기",
                modifier = Modifier.padding(start = DialogTheme.spacing.small),
            )

            AnimatedVisibility(
                visible = isMeetable,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    OfflineDiscussion(
                        place = uiState.place,
                        onPlaceChange = { onIntent(CreateDiscussionIntent.OnPlaceChange(it)) },
                        participantCount = uiState.participantCount,
                        onParticipantCountChange = {
                            onIntent(CreateDiscussionIntent.OnParticipantCountChange(it))
                        },
                        selectedDate = uiState.selectedDate,
                        onDateSelected = { onIntent(CreateDiscussionIntent.OnDateChange(it)) },
                        selectedStartTime = uiState.selectedStartTime,
                        onStartTimeSelected = {
                            onIntent(CreateDiscussionIntent.OnStartTimeChange(it))
                        },
                        selectedEndTime = uiState.selectedEndTime,
                        onEndTimeSelected = { onIntent(CreateDiscussionIntent.OnEndTimeChange(it)) },
                    )
                }
            }

            AnimatedVisibility(
                visible = !isMeetable,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    DialogDropdownMenu(
                        options = uiState.endDateOptions.toImmutableList(),
                        selectedIndex = uiState.selectedEndDateIndex.takeIf { it >= 0 },
                        onSelectedIndexChange = {
                            onIntent(CreateDiscussionIntent.OnEndDateIndexChange(it))
                        },
                        label = "토론 종료 날짜",
                        placeholder = "날짜를 선택해주세요",
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraExtraLarge)) {
                DialogButton(
                    text = "취소",
                    style = DialogButtonStyle.Secondary,
                    onClick = { onIntent(CreateDiscussionIntent.OnCancelClick) },
                    modifier = Modifier.weight(1f),
                )
                DialogButton(
                    text = "등록",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                )
            }
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
    onDateSelected: (LocalDate) -> Unit,
    selectedStartTime: LocalTime?,
    onStartTimeSelected: (LocalTime) -> Unit,
    selectedEndTime: LocalTime?,
    onEndTimeSelected: (LocalTime) -> Unit,
) {
    Column {
        TitleField(
            title = place,
            onTitleChange = onPlaceChange,
            label = "토론 장소",
            placeHolder = "예: 굿샷, 나이스샷, 온라인 줌 미팅, 강남역",
        )

        ParticipantContent(
            participantCount = participantCount,
            onParticipantCountChange = onParticipantCountChange,
        )

        Spacer(modifier = Modifier.height(20.dp))

        DialogDatePicker(
            label = "날짜",
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
        )

        Spacer(modifier = Modifier.height(20.dp))

        DialogTimePicker(
            selectedTime = selectedStartTime,
            onTimeSelected = onStartTimeSelected,
            label = "시작 시간",
        )

        Spacer(modifier = Modifier.height(20.dp))

        DialogTimePicker(
            selectedTime = selectedEndTime,
            onTimeSelected = onEndTimeSelected,
            label = "종료 시간",
        )
    }
}

@Composable
private fun ParticipantContent(
    participantCount: Int,
    onParticipantCountChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "참여자 수",
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
                onValueChange = { if (it.length <= 50) onTitleChange(it) },
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
                text = "${title.length} / 50",
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
            isMeetable = false,
            isMeetableChanged = {},
            onBackClick = {},
            onIntent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateDiscussionScreenPreview2() {
    DialogTheme {
        CreateDiscussionScreen(
            uiState = CreateDiscussionState(),
            isMeetable = true,
            isMeetableChanged = {},
            onBackClick = {},
            onIntent = {},
        )
    }
}

