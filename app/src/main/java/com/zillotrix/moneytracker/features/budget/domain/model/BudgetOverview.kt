package com.zillotrix.moneytracker.features.budget.domain.model

data class BudgetOverview(
    val yearMonth: Int,
    val totalBudget: Long,
    val categories: Map<String, CategoryTotal>
)