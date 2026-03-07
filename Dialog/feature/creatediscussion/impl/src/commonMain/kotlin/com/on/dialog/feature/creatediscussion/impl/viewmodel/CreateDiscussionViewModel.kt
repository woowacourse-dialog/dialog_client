package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import com.on.dialog.model.discussion.draft.OnlineDiscussionDraft
import com.on.dialog.ui.viewmodel.BaseViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class CreateDiscussionViewModel(
    private val discussionRepository: DiscussionRepository,
) : BaseViewModel<CreateDiscussionIntent, CreateDiscussionState, CreateDiscussionEffect>(
    initialState = CreateDiscussionState(),
) {
    override fun onIntent(intent: CreateDiscussionIntent) {
        when (intent) {
            is CreateDiscussionIntent.OnTitleChange -> {
                updateState { copy(title = intent.title) }
            }

            is CreateDiscussionIntent.OnTrackIndexChange -> {
                updateState { copy(selectedTrackIndex = intent.selectedIndex) }
            }

            is CreateDiscussionIntent.OnMeetupEnabledChange -> {
                updateState { copy(isMeetupEnabled = intent.enabled) }
            }

            is CreateDiscussionIntent.OnPlaceChange -> {
                updateState { copy(place = intent.place) }
            }

            is CreateDiscussionIntent.OnParticipantCountChange -> {
                updateState { copy(participantCount = intent.participantCount.coerceAtLeast(2)) }
            }

            is CreateDiscussionIntent.OnDateChange -> {
                updateState { copy(selectedDate = intent.date) }
            }

            is CreateDiscussionIntent.OnStartTimeChange -> {
                updateState { copy(selectedStartTime = intent.time) }
            }

            is CreateDiscussionIntent.OnEndTimeChange -> {
                updateState { copy(selectedEndTime = intent.time) }
            }

            is CreateDiscussionIntent.OnEndDateIndexChange -> {
                updateState { copy(selectedEndDateIndex = intent.selectedIndex) }
            }

            CreateDiscussionIntent.OnCancelClick -> {
                emitEffect(CreateDiscussionEffect.GoBack)
            }

            CreateDiscussionIntent.OnSubmitClick -> {
                if (currentState.isSubmitting) return
                if (!currentState.isSubmitEnabled) return
                submitDiscussion()
            }
        }
    }

    private fun submitDiscussion() {
        updateState { copy(isSubmitting = true) }
        viewModelScope.launch {
            val result = if (currentState.isMeetupEnabled) {
                discussionRepository.createOfflineDiscussion(currentState.toOfflineDraft())
            } else {
                discussionRepository.createOnlineDiscussion(currentState.toOnlineDraft())
            }

            result
                .onSuccess {
                    emitEffect(
                        CreateDiscussionEffect.ShowSnackbar(
                            message = "토론이 등록되었어요.",
                            state = SnackbarState.POSITIVE,
                        ),
                    )
                    emitEffect(CreateDiscussionEffect.GoBack)
                }.onFailure { throwable ->
                    Napier.e(throwable.message.orEmpty(), throwable)
                    emitEffect(
                        CreateDiscussionEffect.ShowSnackbar(
                            message = "토론 등록에 실패했어요. 잠시 후 다시 시도해주세요.",
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }

            updateState { copy(isSubmitting = false) }
        }
    }

    private fun CreateDiscussionState.toOfflineDraft(): OfflineDiscussionDraft =
        OfflineDiscussionDraft(
            title = title.trim(),
            content = title.trim(),
            startAt = selectedDate!!.atTime(selectedStartTime!!.hour, selectedStartTime.minute),
            endAt = selectedDate!!.atTime(selectedEndTime!!.hour, selectedEndTime.minute),
            place = place.trim(),
            maxParticipantCount = participantCount.coerceAtLeast(2),
            category = selectedTrackIndex.toTrackCategory(),
        )

    private fun CreateDiscussionState.toOnlineDraft(): OnlineDiscussionDraft {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val endDate = today.plus(DatePeriod(days = selectedEndDateIndex.toEndDateOffsetDays()))
        return OnlineDiscussionDraft(
            title = title.trim(),
            content = title.trim(),
            endDate = endDate,
            category = selectedTrackIndex.toTrackCategory(),
        )
    }

    private fun Int.toTrackCategory(): String = when (this) {
        0 -> Track.ANDROID.name
        1 -> Track.BACKEND.name
        else -> Track.FRONTEND.name
    }

    private fun Int.toEndDateOffsetDays(): Int = when (this) {
        0 -> 1
        1 -> 2
        else -> 3
    }
}
