package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.dto.CategoryTotalDTO
import com.zillotrix.moneytracker.features.budget.domain.model.CategoryTotal


fun CategoryTotalDTO.toDomain(percentage: Double): CategoryTotal {
    return CategoryTotal(
        budgetCategory = this.budgetCategoryEntity.toDomain(),
        totalPlannedAmount = this.totalPlannedAmount,
        percentage = percentage,
    )
}

fun CategoryTotal.toDTO(): CategoryTotalDTO {
    return CategoryTotalDTO(
        totalPlannedAmount = this.totalPlannedAmount,
        budgetCategoryEntity = this.budgetCategory.toEntity(),
    )
}