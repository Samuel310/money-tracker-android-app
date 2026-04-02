package com.zillotrix.moneytracker.features.budget.domain.model

data class CategoryTotal(
    val categoryId: Int,
    val categoryName: String,
    val totalAmount: Long,
    val percentage: Double
)
