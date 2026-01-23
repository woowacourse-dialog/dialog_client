package com.on.dialog.model.discussion.detail

import com.on.dialog.model.discussion.content.DetailContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod
import com.on.dialog.model.discussion.offline.Participant
import com.on.dialog.model.discussion.participant.ParticipantCapacity
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionDetail(
    override val detailContent: com.on.dialog.model.discussion.content.DetailContent,
    override val summary: String?,
    val dateTimePeriod: com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod,
    val participantCapacity: com.on.dialog.model.discussion.participant.ParticipantCapacity,
    val place: String,
    val participants: List<com.on.dialog.model.discussion.offline.Participant>,
) : com.on.dialog.model.discussion.detail.DiscussionDetail {
    override fun status(now: LocalDateTime): com.on.dialog.model.discussion.content.DiscussionStatus =
        when {
            dateTimePeriod.isBeforeStart(now) -> {
                _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.RECRUITING
            }

            dateTimePeriod.isBeforeStart(now) && participantCapacity.isParticipantFull() -> {
                _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.RECRUITCOMPLETE
            }

            dateTimePeriod.isInPeriod(now) -> {
                _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.INDISCUSSION
            }

            dateTimePeriod.isAfterEnd(now) -> {
                _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.DISCUSSIONCOMPLETE
            }

            else -> {
                _root_ide_package_.com.on.dialog.model.discussion.content.DiscussionStatus.UNDEFINED
            }
        }
}
