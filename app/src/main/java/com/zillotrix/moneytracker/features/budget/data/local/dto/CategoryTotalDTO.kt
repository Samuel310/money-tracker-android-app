package com.zillotrix.moneytracker.features.budget.data.local.dto

data class CategoryTotalDTO(
    val categoryId: Int,
    val categoryName: String,
    val totalPlannedAmount: Long
)