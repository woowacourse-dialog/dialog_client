package com.on.model.discussion.datetimeperiod

import com.on.dialog.core.common.extension.atEndOfDay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline

@JvmInline
value class EndDate(
    val endDate: LocalDate,
) {
    fun isInPeriod(startAt: LocalDateTime, dateTime: LocalDateTime): Boolean =
        dateTime in startAt..endDate.atEndOfDay()

    fun isAfterEnd(dateTime: LocalDateTime): Boolean =
        dateTime > endDate.atEndOfDay()
}
