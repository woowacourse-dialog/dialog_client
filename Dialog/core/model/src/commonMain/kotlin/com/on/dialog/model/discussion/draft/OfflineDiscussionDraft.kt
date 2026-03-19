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
        addAll(DraftPeriod(startAt = startAt, endAt = endAt).validate(today))
        if (maxParticipantCount !in MIN_PARTICIPANT_COUNT..(MAX_PARTICIPANT_COUNT)) {
            add(DraftValidationError.Offline.ParticipantCountOutOfRange)
        }
    }

    companion object {
        const val MIN_PARTICIPANT_COUNT = 2
        const val MAX_PARTICIPANT_COUNT = 10

        const val MAX_TITLE_LENGTH = 50
        const val MAX_PLACE_LENGTH = 50
    }
}
