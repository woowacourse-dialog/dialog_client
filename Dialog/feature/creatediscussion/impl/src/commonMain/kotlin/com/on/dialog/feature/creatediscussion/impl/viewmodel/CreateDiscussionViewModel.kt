package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.feature.creatediscussion.impl.mapper.DiscussionValidator
import com.on.dialog.model.discussion.draft.DiscussionDraft
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
    initialState = CreateDiscussionState.Online(),
) {
    private var offlineModeCache: CreateDiscussionState.Offline = CreateDiscussionState.Offline()
    private var onlineModeCache: CreateDiscussionState.Online = CreateDiscussionState.Online()

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
        if (intent.title.length > DiscussionDraft.MAX_TITLE_LENGTH) return
        updateState { update(title = intent.title) }
    }

    private fun handleContentChange(intent: CreateDiscussionIntent.OnContentChange) {
        updateState { update(content = intent.content) }
    }

    private fun handleTrackIndexChange(intent: CreateDiscussionIntent.OnTrackIndexChange) {
        updateState { update(selectedTrackIndex = intent.selectedIndex) }
    }

    private fun handleMeetupEnabledChange(intent: CreateDiscussionIntent.OnMeetupEnabledChange) {
        updateState {
            if (intent.enabled) toOfflineState(offlineModeCache) else toOnlineState(
                onlineModeCache
            )
        }
    }

    private fun handlePlaceChange(intent: CreateDiscussionIntent.OnPlaceChange) {
        if (intent.place.length > OfflineDiscussionDraft.MAX_PLACE_LENGTH) return
        val currentState = currentState as? CreateDiscussionState.Offline ?: return
        val updatedState = currentState.copy(place = intent.place)
        updateOfflineState(updatedState)
    }

    private fun handleParticipantCountChange(intent: CreateDiscussionIntent.OnParticipantCountChange) {
        val currentState = currentState as? CreateDiscussionState.Offline ?: return
        val updatedState = currentState.copy(
            participantCount = intent.participantCount.coerceIn(
                OfflineDiscussionDraft.MIN_PARTICIPANT_COUNT,
                OfflineDiscussionDraft.MAX_PARTICIPANT_COUNT,
            ),
        )
        updateOfflineState(updatedState)
    }

    private fun handleDateChange(intent: CreateDiscussionIntent.OnDateChange) {
        val currentState = currentState as? CreateDiscussionState.Offline ?: return
        val updated = currentState.copy(selectedDate = intent.date)
        val validated = DiscussionValidator.validateOffline(updated)
        updateOfflineState(validated)
    }

    private fun handleStartTimeChange(intent: CreateDiscussionIntent.OnStartTimeChange) {
        val currentState = currentState as? CreateDiscussionState.Offline ?: return
        val updated = currentState.copy(selectedStartTime = intent.time)
        val validated = DiscussionValidator.validateOffline(updated)
        updateOfflineState(validated)
    }

    private fun handleEndTimeChange(intent: CreateDiscussionIntent.OnEndTimeChange) {
        val currentState = currentState as? CreateDiscussionState.Offline ?: return
        val updated = currentState.copy(selectedEndTime = intent.time)
        val validated = DiscussionValidator.validateOffline(updated)
        updateOfflineState(validated)
    }

    private fun updateOfflineState(updatedState: CreateDiscussionState.Offline) {
        updateState { updatedState.also { offlineModeCache = it } }
    }

    private fun handleEndDateIndexChange(intent: CreateDiscussionIntent.OnEndDateIndexChange) {
        updateState {
            val currentState = this
            if (currentState is CreateDiscussionState.Online) {
                val updatedState = currentState.copy(selectedEndDateIndex = intent.selectedIndex)
                onlineModeCache = updatedState
                updatedState
            } else {
                currentState
            }
        }
    }

    private fun handleSubmitClick() {
        if (currentState.isSubmitting) return
        if (!currentState.isSubmitEnabled) return
        submitDiscussion()
    }

    private fun submitDiscussion() {
        updateState { update(isSubmitting = true) }
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

            updateState { update(isSubmitting = false) }
        }
    }
}
