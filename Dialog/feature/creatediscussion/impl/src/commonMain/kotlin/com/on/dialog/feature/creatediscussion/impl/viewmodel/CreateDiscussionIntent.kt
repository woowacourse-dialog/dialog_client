package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

internal sealed interface CreateDiscussionIntent : UiIntent {
    data class OnTitleChange(
        val title: String,
    ) : CreateDiscussionIntent

    data class OnContentChange(
        val content: String,
    ) : CreateDiscussionIntent

    data class OnTrackIndexChange(
        val selectedIndex: Int,
    ) : CreateDiscussionIntent

    data class OnMeetupEnabledChange(
        val enabled: Boolean,
    ) : CreateDiscussionIntent

    data class OnPlaceChange(
        val place: String,
    ) : CreateDiscussionIntent

    data class OnParticipantCountChange(
        val participantCount: Int,
    ) : CreateDiscussionIntent

    data class OnDateChange(
        val date: LocalDate?,
    ) : CreateDiscussionIntent

    data class OnStartTimeChange(
        val time: LocalTime?,
    ) : CreateDiscussionIntent

    data class OnEndTimeChange(
        val time: LocalTime?,
    ) : CreateDiscussionIntent

    data class OnEndDateIndexChange(
        val selectedIndex: Int,
    ) : CreateDiscussionIntent

    data object OnSubmitClick : CreateDiscussionIntent
}
