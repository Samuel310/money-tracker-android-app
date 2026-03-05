package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity
import com.zillotrix.moneytracker.features.budget.domain.model.Budget

fun BudgetEntity.toDomain(): Budget {
    return Budget(
        id = this.id,
        name = this.name,
        amount = this.amount,
        categoryId = this.categoryId,
        monthYear = this.monthYear
    )
}

fun Budget.toEntity(): BudgetEntity {
    return BudgetEntity(
        id = this.id,
        name = this.name,
        amount = this.amount,
        categoryId = this.categoryId,
        monthYear = this.monthYear
    )
}