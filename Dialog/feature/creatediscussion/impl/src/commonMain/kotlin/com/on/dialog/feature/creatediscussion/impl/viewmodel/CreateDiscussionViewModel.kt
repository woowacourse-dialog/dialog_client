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
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

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

            is CreateDiscussionIntent.OnContentChange -> {
                updateState { copy(content = intent.content) }
            }

            is CreateDiscussionIntent.OnTrackIndexChange -> {
                updateState { copy(selectedTrackIndex = intent.selectedIndex) }
            }

            is CreateDiscussionIntent.OnMeetupEnabledChange -> {
                updateState {
                    copy(
                        mode = if (intent.enabled) Offline() else Online(),
                    )
                }
            }

            is CreateDiscussionIntent.OnPlaceChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        copy(mode = currentMode.copy(place = intent.place))
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnParticipantCountChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        copy(
                            mode = currentMode.copy(
                                participantCount = intent.participantCount.coerceAtLeast(2),
                            ),
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
                        copy(mode = DiscussionValidator.validateOffline(updated))
                    } else this
                }
            }

            is CreateDiscussionIntent.OnStartTimeChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updated = currentMode.copy(selectedStartTime = intent.time)
                        copy(mode = DiscussionValidator.validateOffline(updated))
                    } else this
                }
            }

            is CreateDiscussionIntent.OnEndTimeChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        val updated = currentMode.copy(selectedEndTime = intent.time)
                        copy(mode = DiscussionValidator.validateOffline(updated))
                    } else this
                }
            }

            is CreateDiscussionIntent.OnEndDateIndexChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Online) {
                        copy(mode = currentMode.copy(selectedEndDateIndex = intent.selectedIndex))
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
                            message = "토론이 등록되었어요.",
                            state = SnackbarState.POSITIVE,
                        ),
                    )
                    emitEffect(CreateDiscussionEffect.NavigateToDetail(discussionId = discussionId))
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
}
