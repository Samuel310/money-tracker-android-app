package com.zillotrix.moneytracker.features.budget.domain.model

data class BudgetInfo(
    val id: Long,
    val name: String,
    val amount: Long,
    val yearMonth: Int,
    val totalAmtSpent: Long,
    val budgetCategory: BudgetCategory
)

