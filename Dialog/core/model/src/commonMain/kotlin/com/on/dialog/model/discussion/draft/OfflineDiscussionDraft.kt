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
        if (startAt <= today) {
            add(DraftValidationError.Offline.StartDateNotAfterToday)
        }
        if (endAt <= today) {
            add(DraftValidationError.Offline.EndDateNotAfterToday)
        }
        if (startAt >= endAt) {
            add(DraftValidationError.Offline.StartNotBeforeEnd)
        }
        if (startAt.hour !in MIN_START_HOUR..MAX_END_HOUR) {
            add(DraftValidationError.Offline.StartTimeOutOfRange)
        }
        if (endAt.hour !in MIN_START_HOUR..MAX_END_HOUR) {
            add(DraftValidationError.Offline.EndTimeOutOfRange)
        }
        if (maxParticipantCount !in MIN_PARTICIPANT_COUNT..(MAX_PARTICIPANT_COUNT)) {
            add(DraftValidationError.Offline.ParticipantCountOutOfRange)
        }
    }

    companion object {
        const val MIN_PARTICIPANT_COUNT = 2
        const val MAX_PARTICIPANT_COUNT = 10
        private const val MIN_START_HOUR = 8
        private const val MAX_END_HOUR = 23

        const val MAX_TITLE_LENGTH = 50
        const val MAX_PLACE_LENGTH = 50
    }
}
