package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity
import com.zillotrix.moneytracker.features.budget.data.local.dto.BudgetWithCategoryAndExpensesDTO
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo

fun BudgetWithCategoryAndExpensesDTO.toDomain(): BudgetInfo {
    return BudgetInfo(
        id = this.budget.id,
        name = this.budget.name,
        amount = this.budget.amount,
        yearMonth = this.budget.yearMonth,
        totalAmtSpent = this.totalAmtSpent,
        budgetCategory = this.category.toDomain(),
    )
}

fun BudgetInfo.toDTO(): BudgetWithCategoryAndExpensesDTO {
    return BudgetWithCategoryAndExpensesDTO(
        budget = BudgetEntity(
            id = this.id,
            name = this.name,
            amount = this.amount,
            categoryId = this.budgetCategory.id,
            yearMonth = this.yearMonth,
        ),
        category = this.budgetCategory.toEntity(),
        totalAmtSpent = this.totalAmtSpent,
    )
}