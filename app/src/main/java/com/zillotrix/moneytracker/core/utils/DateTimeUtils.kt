package com.zillotrix.moneytracker.core.utils

import java.time.YearMonth

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