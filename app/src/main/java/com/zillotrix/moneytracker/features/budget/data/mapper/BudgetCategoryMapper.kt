package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory


fun BudgetCategoryEntity.toDomain(): BudgetCategory {
    return BudgetCategory(
        id = this.id,
        name = this.name,
    )
}

fun BudgetCategory.toEntity(): BudgetCategoryEntity {
    return BudgetCategoryEntity(
        id = this.id,
        name = this.name,
    )
}