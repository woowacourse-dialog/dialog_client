package com.on.model.discussion.datetimeperiod

import kotlinx.datetime.LocalDateTime

data class DateTimePeriod(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
) {
    fun isBeforeStart(dateTime: LocalDateTime): Boolean =
        dateTime < startAt

    fun isInPeriod(dateTime: LocalDateTime): Boolean =
        dateTime in startAt..endAt

    fun isAfterEnd(dateTime: LocalDateTime): Boolean =
        dateTime > endAt
}