package com.zillotrix.moneytracker.core.utils

import java.util.Locale

fun Long.toDisplayAmount(): String {
    return "₹${String.format(Locale.getDefault(),"%.2f", this / 100.0)}"
}

fun String.toPaisa(): Long {
    return this.toDoubleOrNull()?.let {
        (it * 100).toLong()
    } ?: 0L
}