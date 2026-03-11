package com.zillotrix.moneytracker.core.utils

import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId

fun YearMonth.toIntYYYYMM(): Int {
    return this.year * 100 + this.monthValue
}

fun Int.toYearMonth(): YearMonth {
    val year = this / 100
    val month = this % 100
    return YearMonth.of(year, month)
}

fun YearMonth.toMonthName(locale: java.util.Locale = java.util.Locale.getDefault()): String {
    return this.month.getDisplayName(java.time.format.TextStyle.FULL, locale)
}

fun YearMonth.toYearString(): String {
    return this.year.toString()
}

fun YearMonth.getMonthRange(): Pair<Long, Long> {
    val ym = this

    val startOfMonth = ym.atDay(1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val endOfMonth = ym.atEndOfMonth()
        .atTime(LocalTime.MAX)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return Pair(startOfMonth, endOfMonth)
}