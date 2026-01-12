package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionStatus
import com.on.model.discussion.datetimeperiod.DateTimePeriod
import com.on.model.discussion.participant.ParticipantCapacity
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionCatalog(
    override val catalogContent: CatalogContent,
    val dateTimePeriod: DateTimePeriod,
    val participantCapacity: ParticipantCapacity,
    val place: String,
) : DiscussionCatalog {
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
