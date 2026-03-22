package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDateTime

data class DraftPeriod(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
) {
    fun validate(today: LocalDateTime): List<DraftValidationError.Offline> = buildList {
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
    }

    companion object {
        private const val MIN_START_HOUR = 8
        private const val MAX_END_HOUR = 23
    }
}
