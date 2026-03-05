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