package com.on.dialog.model.discussion.draft

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

    override fun validate(today: LocalDateTime): List<DraftValidationError> = buildList {
        if (startAt <= today)
            add(DraftValidationError.Offline.StartDateNotAfterToday)
        if (endAt <= today)
            add(DraftValidationError.Offline.EndDateNotAfterToday)
        if (startAt >= endAt)
            add(DraftValidationError.Offline.StartNotBeforeEnd)
        if (startAt.hour !in 8..<23)
            add(DraftValidationError.Offline.StartTimeOutOfRange)
        if (endAt.hour !in 8..<23)
            add(DraftValidationError.Offline.EndTimeOutOfRange)
        if (maxParticipantCount < 2)
            add(DraftValidationError.Offline.ParticipantCountTooLow)
    }
}