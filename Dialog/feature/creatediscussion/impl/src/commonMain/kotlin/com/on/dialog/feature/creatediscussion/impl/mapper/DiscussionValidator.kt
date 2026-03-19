package com.on.dialog.feature.creatediscussion.impl.mapper

import com.on.dialog.core.common.extension.now
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionState
import com.on.dialog.model.discussion.draft.DraftValidationError
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import dialog.feature.creatediscussion.impl.generated.resources.Res
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_end_after_start
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_end_time_range
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_participant_range
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_past_date
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_start_time_range
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_error_time_after_now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.StringResource
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal object DiscussionValidator {
    fun validateOffline(offlineState: CreateDiscussionState.Offline): CreateDiscussionState.Offline {
        val now = LocalDateTime.now()
        val date = offlineState.selectedDate
        val startTime = offlineState.selectedStartTime
        val endTime = offlineState.selectedEndTime

        val offlineErrors = offlineState.collectOfflineValidationErrors(now)

        // Date 단독 검증은 명시적으로 처리하고, 시각 관련은 Domain validation 결과를 사용한다.
        val dateError = date?.let {
            when {
                it < now.date -> Res.string.create_discussion_error_past_date
                else -> null
            }
        }

        val startTimeError = startTime?.let {
            offlineErrors
                .firstOrNull {
                    it is DraftValidationError.Offline.StartTimeOutOfRange ||
                            it is DraftValidationError.Offline.StartDateNotAfterToday
                }?.toMessage()
        }

        val endTimeError = endTime?.let {
            offlineErrors
                .firstOrNull {
                    it is DraftValidationError.Offline.EndTimeOutOfRange ||
                            it is DraftValidationError.Offline.EndDateNotAfterToday ||
                            (startTime != null && it is DraftValidationError.Offline.StartNotBeforeEnd)
                }?.toMessage()
        }

        return offlineState.copy(
            selectedDateErrorMessage = dateError,
            selectedStartTimeErrorMessage = startTimeError,
            selectedEndTimeErrorMessage = endTimeError,
        )
    }
}

private fun CreateDiscussionState.Offline.collectOfflineValidationErrors(
    now: LocalDateTime,
): List<DraftValidationError.Offline> {
    val date = selectedDate ?: now.date
    val start = selectedStartTime
    val end = selectedEndTime

    val draft = when {
        start != null && end != null -> OfflineDiscussionDraft(
            title = "",
            content = "",
            category = "",
            startAt = LocalDateTime(date, start),
            endAt = LocalDateTime(date, end),
            place = place,
            maxParticipantCount = participantCount,
        )

        start != null -> OfflineDiscussionDraft(
            title = "",
            content = "",
            category = "",
            startAt = LocalDateTime(date, start),
            endAt = LocalDateTime(date, LocalTime(23, 59)),
            place = place,
            maxParticipantCount = participantCount,
        )

        end != null -> OfflineDiscussionDraft(
            title = "",
            content = "",
            category = "",
            startAt = LocalDateTime(date, LocalTime(0, 0)),
            endAt = LocalDateTime(date, end),
            place = place,
            maxParticipantCount = participantCount,
        )

        else -> return emptyList()
    }

    return draft.validate(now).filterIsInstance<DraftValidationError.Offline>()
}

private fun DraftValidationError.Offline.toMessage(): StringResource = when (this) {
    DraftValidationError.Offline.StartNotBeforeEnd -> Res.string.create_discussion_error_end_after_start
    DraftValidationError.Offline.StartTimeOutOfRange -> Res.string.create_discussion_error_start_time_range
    DraftValidationError.Offline.EndTimeOutOfRange -> Res.string.create_discussion_error_end_time_range
    DraftValidationError.Offline.ParticipantCountOutOfRange -> Res.string.create_discussion_error_participant_range
    DraftValidationError.Offline.StartDateNotAfterToday -> Res.string.create_discussion_error_time_after_now
    DraftValidationError.Offline.EndDateNotAfterToday -> Res.string.create_discussion_error_time_after_now
}
