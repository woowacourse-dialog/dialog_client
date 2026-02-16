package com.on.dialog.discussiondetail.impl.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

/**
 * [LocalDate]를 한국어 형식의 문자열로 변환합니다.
 *
 * @return 예: "2026년 1월 30일"
 */
fun LocalDate.toKoreanString(): String =
    "${year}년 ${month.number}월 ${day}일"

/**
 * [LocalDateTime]을 한국어 형식의 문자열로 변환합니다.
 *
 * @param omitZeroMinute true이면 분이 0일 때 분을 생략합니다.
 * @return 예: "2026년 1월 30일 12시 30분" 또는 "2026년 1월 30일 12시" (분이 0이고 [omitZeroMinute]가 true인 경우)
 */
fun LocalDateTime.toKoreanString(omitZeroMinute: Boolean = true): String {
    val base = "${year}년 ${month.number}월 ${day}일 ${hour}시"
    return if (omitZeroMinute && minute == 0) base else "$base ${minute}분"
}

/**
 * 두 [LocalDateTime] 사이의 기간을 한국어 형식의 문자열로 변환합니다.
 *
 * 시작과 종료 시점에서 동일한 부분(년, 월, 일)은 종료 시점 표기에서 생략됩니다.
 *
 * @param start 시작 시점
 * @param end 종료 시점
 * @return 예: "2026년 1월 30일 12시 ~ 14시", "2026년 1월 30일 12시 ~ 31일 14시"
 */
fun periodToKoreanString(
    start: LocalDateTime,
    end: LocalDateTime,
): String {
    val startStr = start.toKoreanString()

    val endStr = buildString {
        when {
            start.year != end.year -> append("${end.year}년 ${end.month.number}월 ${end.day}일 ${end.hour}시")
            start.month != end.month -> append("${end.month.number}월 ${end.day}일 ${end.hour}시")
            start.day != end.day -> append("${end.day}일 ${end.hour}시")
            else -> append("${end.hour}시")
        }
        if (end.minute != 0) append(" ${end.minute}분")
    }

    return "$startStr ~ $endStr"
}
