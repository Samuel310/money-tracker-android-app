package com.zillotrix.moneytracker.features.budget.domain.model

data class BudgetInfo(
    val id: Long,
    val name: String,
    val amount: Long,
    val categoryId: Long,
    val categoryName: String,
    val monthYear: Int,
)
