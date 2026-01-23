package com.on.dialog.model.discussion.detail

import com.on.dialog.model.discussion.content.DetailContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod
import com.on.dialog.model.discussion.offline.Participant
import com.on.dialog.model.discussion.participant.ParticipantCapacity
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val dateTimePeriod: DateTimePeriod,
    val participantCapacity: ParticipantCapacity,
    val place: String,
    val participants: List<Participant>,
) : DiscussionDetail {
    override fun status(now: LocalDateTime): DiscussionStatus =
        when {
            dateTimePeriod.isBeforeStart(now) -> {
                DiscussionStatus.RECRUITING
            }

            dateTimePeriod.isBeforeStart(now) && participantCapacity.isParticipantFull() -> {
                DiscussionStatus.RECRUITCOMPLETE
            }

            dateTimePeriod.isInPeriod(now) -> {
                DiscussionStatus.INDISCUSSION
            }

            dateTimePeriod.isAfterEnd(now) -> {
                DiscussionStatus.DISCUSSIONCOMPLETE
            }

            else -> {
                DiscussionStatus.UNDEFINED
            }
        }
}
