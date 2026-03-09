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
            is CreateDiscussionIntent.OnTitleChange -> {
                if (intent.title.length > 50) return
                updateState { copy(title = intent.title) }
            }

            is CreateDiscussionIntent.OnContentChange -> {
                updateState { copy(content = intent.content) }
            }

            is CreateDiscussionIntent.OnTrackIndexChange -> {
                updateState { copy(selectedTrackIndex = intent.selectedIndex) }
            }

            is CreateDiscussionIntent.OnMeetupEnabledChange -> {
                updateState {
                    copy(
                        mode = if (intent.enabled) offlineModeCache else onlineModeCache,
                    )
                }
            }

            is CreateDiscussionIntent.OnPlaceChange -> {
                if (intent.place.length > 50) return
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updatedMode = currentMode.copy(place = intent.place)
                        offlineModeCache = updatedMode
                        copy(mode = updatedMode)
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnParticipantCountChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updatedMode = currentMode.copy(
                            participantCount = intent.participantCount.coerceIn(2, 10),
                        )
                        offlineModeCache = updatedMode
                        copy(
                            mode = updatedMode,
                        )
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnDateChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updated = currentMode.copy(selectedDate = intent.date)
                        val validated = DiscussionValidator.validateOffline(updated)
                        offlineModeCache = validated
                        copy(mode = validated)
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnStartTimeChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updated = currentMode.copy(selectedStartTime = intent.time)
                        val validated = DiscussionValidator.validateOffline(updated)
                        offlineModeCache = validated
                        copy(mode = validated)
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnEndTimeChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updated = currentMode.copy(selectedEndTime = intent.time)
                        val validated = DiscussionValidator.validateOffline(updated)
                        offlineModeCache = validated
                        copy(mode = validated)
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnEndDateIndexChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Online) {
                        val updatedMode = currentMode.copy(selectedEndDateIndex = intent.selectedIndex)
                        onlineModeCache = updatedMode
                        copy(mode = updatedMode)
                    } else {
                        this
                    }
                }
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
            val draft = currentState.toDomain()
            val result = when (draft) {
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
