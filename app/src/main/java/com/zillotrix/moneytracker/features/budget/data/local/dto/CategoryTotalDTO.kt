package com.zillotrix.moneytracker.features.budget.data.local.dto

import androidx.room.Embedded
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity

data class CategoryTotalDTO(
    @Embedded
    val budgetCategoryEntity: BudgetCategoryEntity,
    val totalPlannedAmount: Long
)