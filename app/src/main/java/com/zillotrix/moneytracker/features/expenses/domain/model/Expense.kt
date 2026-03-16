package com.zillotrix.moneytracker.features.expenses.domain.model

import java.util.Date

data class Expense(
    val id: Long,
    val name: String,
    val amount: Long,
    val budgetId: Long,
    val date: Date
)
