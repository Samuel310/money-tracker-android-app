package com.zillotrix.moneytracker.core.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun YearMonth.toIntYYYYMM(): Int {
    return this.year * 100 + this.monthValue
}

fun Int.toYearMonth(): YearMonth {
    val year = this / 100
    val month = this % 100
    return YearMonth.of(year, month)
}

fun YearMonth.toMonthName(locale: Locale = Locale.getDefault()): String {
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

fun Date.toDayMonthYearFull(): String {
    val cal = Calendar.getInstance().apply { time = this@toDayMonthYearFull }
    val day = cal.get(Calendar.DAY_OF_MONTH)

    val suffix = when {
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }

    val dayName = SimpleDateFormat("EEE", Locale.getDefault()).format(this)
    val monthYear = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(this)

    return "$dayName, $day$suffix $monthYear"
}

fun YearMonth.toShortMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale.getDefault())
    return this.format(formatter)
}