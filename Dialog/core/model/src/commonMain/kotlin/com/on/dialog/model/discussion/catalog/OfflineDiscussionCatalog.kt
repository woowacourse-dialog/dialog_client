package com.on.dialog.model.discussion.catalog

import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod
import com.on.dialog.model.discussion.participant.ParticipantCapacity
import kotlinx.datetime.LocalDateTime

data class OfflineDiscussionCatalog(
    override val catalogContent: com.on.dialog.model.discussion.content.CatalogContent,
    val dateTimePeriod: com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod,
    val participantCapacity: com.on.dialog.model.discussion.participant.ParticipantCapacity,
    val place: String,
) : com.on.dialog.model.discussion.catalog.DiscussionCatalog {
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
