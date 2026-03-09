package com.on.dialog.feature.creatediscussion.impl.mapper

import com.on.dialog.feature.creatediscussion.impl.viewmodel.DiscussionMode
import com.on.dialog.model.discussion.draft.DraftValidationError
import com.on.dialog.model.discussion.draft.OfflineDiscussionDraft
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
            if (date < now.date) "이미 지난 날짜 입니다." else ""
        } else ""

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
                }?.toMessage() ?: ""
        } else {
            ""
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
                }?.toMessage() ?: ""
        } else {
            ""
        }

        return mode.copy(
            selectedDateErrorMessage = dateError,
            selectedStartTimeErrorMessage = startTimeError,
            selectedEndTimeErrorMessage = endTimeError,
        )
    }
}

private fun DraftValidationError.toMessage(): String = when (this) {
    DraftValidationError.Offline.StartNotBeforeEnd -> "종료 시간은 시작 시간보다 늦어야 해요."
    DraftValidationError.Offline.StartTimeOutOfRange -> "시작 시간은 오전 8시 ~ 오후 11시 사이여야 해요."
    DraftValidationError.Offline.EndTimeOutOfRange -> "종료 시간은 오전 8시 ~ 오후 11시 사이여야 해요."
    DraftValidationError.Offline.ParticipantCountTooLow -> "최소 2명 이상이어야 해요."
    DraftValidationError.Offline.StartDateNotAfterToday -> "오늘 이후 날짜를 선택해주세요."
    DraftValidationError.Offline.EndDateNotAfterToday -> "오늘 이후 날짜를 선택해주세요."
    DraftValidationError.Online.EndDateNotAfterToday -> "오늘 이후 날짜를 선택해주세요."
}
