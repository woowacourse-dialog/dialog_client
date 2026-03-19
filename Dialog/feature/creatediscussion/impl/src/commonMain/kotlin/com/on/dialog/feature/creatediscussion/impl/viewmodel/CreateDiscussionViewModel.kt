package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.feature.creatediscussion.impl.mapper.DiscussionValidator
import com.on.dialog.feature.creatediscussion.impl.viewmodel.DiscussionMode.Offline
import com.on.dialog.feature.creatediscussion.impl.viewmodel.DiscussionMode.Online
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import com.on.dialog.model.discussion.draft.OnlineDiscussionDraft
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.creatediscussion.impl.generated.resources.Res
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_snackbar_failure
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_snackbar_success
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

internal class CreateDiscussionViewModel(
    private val discussionRepository: DiscussionRepository,
) : BaseViewModel<CreateDiscussionIntent, CreateDiscussionState, CreateDiscussionEffect>(
    initialState = CreateDiscussionState(),
) {
    private var offlineModeCache: Offline = Offline()
    private var onlineModeCache: Online = Online()

    override fun onIntent(intent: CreateDiscussionIntent) {
        when (intent) {
            is CreateDiscussionIntent.OnTitleChange -> handleTitleChange(intent)

            is CreateDiscussionIntent.OnContentChange -> handleContentChange(intent)

            is CreateDiscussionIntent.OnTrackIndexChange -> handleTrackIndexChange(intent)

            is CreateDiscussionIntent.OnMeetupEnabledChange -> handleMeetupEnabledChange(intent)

            is CreateDiscussionIntent.OnPlaceChange -> handlePlaceChange(intent)

            is CreateDiscussionIntent.OnParticipantCountChange -> handleParticipantCountChange(
                intent,
            )

            is CreateDiscussionIntent.OnDateChange -> handleDateChange(intent)

            is CreateDiscussionIntent.OnStartTimeChange -> handleStartTimeChange(intent)

            is CreateDiscussionIntent.OnEndTimeChange -> handleEndTimeChange(intent)

            is CreateDiscussionIntent.OnEndDateIndexChange -> handleEndDateIndexChange(intent)

            CreateDiscussionIntent.OnSubmitClick -> handleSubmitClick()
        }
    }

    private fun handleTitleChange(intent: CreateDiscussionIntent.OnTitleChange) {
        if (intent.title.length > OfflineDiscussionDraft.MAX_TITLE_LENGTH) return
        updateState { copy(title = intent.title) }
    }

    private fun handleContentChange(intent: CreateDiscussionIntent.OnContentChange) {
        updateState { copy(content = intent.content) }
    }

    private fun handleTrackIndexChange(intent: CreateDiscussionIntent.OnTrackIndexChange) {
        updateState { copy(selectedTrackIndex = intent.selectedIndex) }
    }

    private fun handleMeetupEnabledChange(intent: CreateDiscussionIntent.OnMeetupEnabledChange) {
        updateState {
            copy(
                mode = if (intent.enabled) offlineModeCache else onlineModeCache,
            )
        }
    }

    private fun handlePlaceChange(intent: CreateDiscussionIntent.OnPlaceChange) {
        if (intent.place.length > OfflineDiscussionDraft.MAX_PLACE_LENGTH) return
        val currentMode = currentState.mode as? Offline ?: return
        val updatedMode = currentMode.copy(place = intent.place)
        updateOfflineMode(updatedMode)
    }

    private fun handleParticipantCountChange(intent: CreateDiscussionIntent.OnParticipantCountChange) {
        val currentMode = currentState.mode as? Offline ?: return
        val updatedMode = currentMode.copy(
            participantCount = intent.participantCount.coerceIn(
                OfflineDiscussionDraft.MIN_PARTICIPANT_COUNT,
                OfflineDiscussionDraft.MAX_PARTICIPANT_COUNT,
            ),
        )
        updateOfflineMode(updatedMode)
    }

    private fun handleDateChange(intent: CreateDiscussionIntent.OnDateChange) {
        val currentMode = currentState.mode as? Offline ?: return
        val updated = currentMode.copy(selectedDate = intent.date)
        val validated = DiscussionValidator.validateOffline(updated)
        updateOfflineMode(validated)
    }

    private fun handleStartTimeChange(intent: CreateDiscussionIntent.OnStartTimeChange) {
        val currentMode = currentState.mode as? Offline ?: return
        val updated = currentMode.copy(selectedStartTime = intent.time)
        val validated = DiscussionValidator.validateOffline(updated)
        updateOfflineMode(validated)
    }

    private fun handleEndTimeChange(intent: CreateDiscussionIntent.OnEndTimeChange) {
        val currentMode = currentState.mode as? Offline ?: return
        val updated = currentMode.copy(selectedEndTime = intent.time)
        val validated = DiscussionValidator.validateOffline(updated)
        updateOfflineMode(validated)
    }

    private fun updateOfflineMode(updatedMode: Offline) {
        updateState {
            offlineModeCache = updatedMode
            copy(mode = updatedMode)
        }
    }

    private fun handleEndDateIndexChange(intent: CreateDiscussionIntent.OnEndDateIndexChange) {
        updateState {
            val currentMode = mode
            if (currentMode is Online) {
                val updatedMode = currentMode.copy(selectedEndDateIndex = intent.selectedIndex)
                onlineModeCache = updatedMode
                copy(mode = updatedMode)
            } else {
                this
            }
        }
    }

    private fun handleSubmitClick() {
        if (currentState.isSubmitting) return
        if (!currentState.isSubmitEnabled) return
        submitDiscussion()
    }

    private fun submitDiscussion() {
        updateState { copy(isSubmitting = true) }
        viewModelScope.launch {
            val result = when (val draft = currentState.toDomain()) {
                is OfflineDiscussionDraft -> discussionRepository.createOfflineDiscussion(draft)
                is OnlineDiscussionDraft -> discussionRepository.createOnlineDiscussion(draft)
            }

            result
                .onSuccess { discussionId: Long ->
                    emitEffect(
                        CreateDiscussionEffect.ShowSnackbar(
                            message = Res.string.create_discussion_snackbar_success,
                            state = SnackbarState.POSITIVE,
                        ),
                    )
                    emitEffect(CreateDiscussionEffect.NavigateToDetail(discussionId = discussionId))
                }.onFailure { throwable ->
                    Napier.e(throwable.message.orEmpty(), throwable)
                    emitEffect(
                        CreateDiscussionEffect.ShowSnackbar(
                            message = Res.string.create_discussion_snackbar_failure,
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }

            updateState { copy(isSubmitting = false) }
        }
    }
}
