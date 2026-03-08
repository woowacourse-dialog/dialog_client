package com.on.dialog.feature.creatediscussion.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.DiscussionRepository
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

            is CreateDiscussionIntent.OnTrackIndexChange -> {
                updateState { copy(selectedTrackIndex = intent.selectedIndex) }
            }

            is CreateDiscussionIntent.OnMeetupEnabledChange -> {
                updateState {
                    copy(
                        mode = if (intent.enabled) DiscussionMode.Offline() else DiscussionMode.Online(),
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
                        copy(mode = currentMode.copy(selectedDate = intent.date))
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnStartTimeChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        copy(mode = currentMode.copy(selectedStartTime = intent.time))
                    } else {
                        this
                    }
                }
            }

            is CreateDiscussionIntent.OnEndTimeChange -> {
                updateState {
                    val currentMode = mode
                    if (currentMode is DiscussionMode.Offline) {
                        copy(mode = currentMode.copy(selectedEndTime = intent.time))
                    } else {
                        this
                    }
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
            val result = when (val currentMode = currentState.mode) {
                is DiscussionMode.Offline -> {
                    discussionRepository.createOfflineDiscussion(
                        request = currentState.toDomain(currentMode),
                    )
                }

                is DiscussionMode.Online -> {
                    discussionRepository.createOnlineDiscussion(
                        request = currentState.toDomain(currentMode),
                    )
                }
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
}
