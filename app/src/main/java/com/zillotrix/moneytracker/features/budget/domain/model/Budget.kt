package com.zillotrix.moneytracker.features.budget.domain.model

data class Budget(
    val id: Long,
    val name: String,
    val amount: Long,
    val categoryId: Long,
    val monthYear: Int,
)
