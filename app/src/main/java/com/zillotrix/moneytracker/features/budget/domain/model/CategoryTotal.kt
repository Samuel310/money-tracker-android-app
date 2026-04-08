package com.zillotrix.moneytracker.features.budget.domain.model

data class CategoryTotal(
    val categoryId: Int,
    val categoryName: String,
    val totalPlannedAmount: Long,
    val percentage: Double
)
