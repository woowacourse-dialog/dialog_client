package com.on.dialog.feature.creatediscussion.impl.mapper

import com.on.dialog.feature.creatediscussion.impl.viewmodel.DiscussionMode
import com.on.dialog.model.discussion.draft.DraftValidationError
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import dialog.feature.creatediscussion.impl.generated.resources.Res
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_date_after_today
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_end_after_start
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_end_time_range
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_participant_min
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_past_date
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_start_time_range
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal object DiscussionValidator {
    fun validateOffline(mode: DiscussionMode.Offline): DiscussionMode.Offline {
        val now = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val date = mode.selectedDate
        val startTime = mode.selectedStartTime
        val endTime = mode.selectedEndTime

        // Domain에서 Date를 따로 저장하지 않아 dateError는 따로 처리
        val dateError = if (date != null) {
            if (date < now.date) Res.string.create_discussion_error_past_date else null
        } else null

        val startTimeError = if (startTime != null) {
            val draft = OfflineDiscussionDraft(
                title = "",
                content = "",
                category = "",
                startAt = LocalDateTime(date ?: LocalDate(9999, 1, 1), startTime),
                endAt = LocalDateTime(date ?: LocalDate(9999, 1, 1), endTime ?: LocalTime(23, 59)),
                place = mode.place,
                maxParticipantCount = mode.participantCount,
            )
            draft
                .validate(now)
                .firstOrNull {
                    it is DraftValidationError.Offline.StartTimeOutOfRange ||
                            it is DraftValidationError.Offline.StartNotBeforeEnd
                }?.toMessage()
        } else {
            null
        }

        val endTimeError = if (endTime != null) {
            val draft = OfflineDiscussionDraft(
                title = "",
                content = "",
                category = "",
                startAt = LocalDateTime(
                    date ?: LocalDate(9999, 1, 1),
                    startTime ?: LocalTime(0, 0),
                ),
                endAt = LocalDateTime(date ?: LocalDate(9999, 1, 1), endTime),
                place = mode.place,
                maxParticipantCount = mode.participantCount,
            )
            draft
                .validate(now)
                .firstOrNull {
                    it is DraftValidationError.Offline.EndTimeOutOfRange ||
                            it is DraftValidationError.Offline.StartNotBeforeEnd
                }?.toMessage()
        } else {
            null
        }

        return mode.copy(
            selectedDateErrorMessage = dateError,
            selectedStartTimeErrorMessage = startTimeError,
            selectedEndTimeErrorMessage = endTimeError,
        )
    }
}

private fun DraftValidationError.toMessage(): StringResource = when (this) {
    DraftValidationError.Offline.StartNotBeforeEnd -> Res.string.create_discussion_error_end_after_start
    DraftValidationError.Offline.StartTimeOutOfRange -> Res.string.create_discussion_error_start_time_range
    DraftValidationError.Offline.EndTimeOutOfRange -> Res.string.create_discussion_error_end_time_range
    DraftValidationError.Offline.ParticipantCountTooLow -> Res.string.create_discussion_error_participant_min
    DraftValidationError.Offline.StartDateNotAfterToday -> Res.string.create_discussion_error_date_after_today
    DraftValidationError.Offline.EndDateNotAfterToday -> Res.string.create_discussion_error_date_after_today
    DraftValidationError.Online.EndDateNotAfterToday -> Res.string.create_discussion_error_date_after_today
}
