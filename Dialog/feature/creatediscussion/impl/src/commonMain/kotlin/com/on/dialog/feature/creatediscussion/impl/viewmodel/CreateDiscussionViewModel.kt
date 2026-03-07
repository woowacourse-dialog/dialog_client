package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.ui.viewmodel.BaseViewModel

internal class CreateDiscussionViewModel :
    BaseViewModel<CreateDiscussionIntent, CreateDiscussionState, CreateDiscussionEffect>(
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

            is CreateDiscussionIntent.OnPlaceChange -> {
                updateState { copy(place = intent.place) }
            }

            is CreateDiscussionIntent.OnParticipantCountChange -> {
                updateState { copy(participantCount = intent.participantCount.coerceAtLeast(0)) }
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
                updateState { CreateDiscussionState() }
            }
        }
    }
}
