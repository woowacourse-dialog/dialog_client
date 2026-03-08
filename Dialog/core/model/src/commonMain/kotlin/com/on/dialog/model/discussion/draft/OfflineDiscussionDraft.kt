package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionDraft(
    override val title: String,
    override val content: String,
    override val category: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val place: String,
    val maxParticipantCount: Int,
) : DiscussionDraft {

    override fun validate(today: LocalDate): List<DraftValidationError> = buildList {
        if (startAt.date <= today)
            add(DraftValidationError.Offline.StartDateNotAfterToday)
        if (endAt.date <= today)
            add(DraftValidationError.Offline.EndDateNotAfterToday)
        if (startAt >= endAt)
            add(DraftValidationError.Offline.StartNotBeforeEnd)
        if (startAt.hour < 8 || startAt.hour >= 23)
            add(DraftValidationError.Offline.StartTimeOutOfRange)
        if (endAt.hour < 8 || endAt.hour >= 23)
            add(DraftValidationError.Offline.EndTimeOutOfRange)
        if (maxParticipantCount < 2)
            add(DraftValidationError.Offline.ParticipantCountTooLow)
    }
}