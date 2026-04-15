package com.zillotrix.moneytracker.features.budget.domain.model

data class CategoryTotal(
    val budgetCategory: BudgetCategory,
    val totalPlannedAmount: Long,
    val percentage: Double
)
