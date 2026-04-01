package com.zillotrix.moneytracker.features.budget.domain.model

data class BudgetOverview(
    val yearMonth: Int,
    val totalBudget: Double,
    val categories: Map<String, CategoryTotal>
)