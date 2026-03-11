package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity
import com.zillotrix.moneytracker.features.budget.data.local.relation.BudgetWithCategoryAndExpensesRelation
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo

fun BudgetWithCategoryAndExpensesRelation.toDomain(): BudgetInfo {
    return BudgetInfo(
        id = this.budget.id,
        name = this.budget.name,
        amount = this.budget.amount,
        categoryId = this.budget.categoryId,
        monthYear = this.budget.monthYear,
        categoryName = this.category.name,
        totalAmtSpent = this.totalAmtSpent,
    )
}

fun BudgetInfo.toEntity(): BudgetWithCategoryAndExpensesRelation {
    return BudgetWithCategoryAndExpensesRelation(
        budget = BudgetEntity(
            id = this.id,
            name = this.name,
            amount = this.amount,
            categoryId = this.categoryId,
            monthYear = this.monthYear,
        ),
        category = BudgetCategoryEntity(
            id = this.categoryId,
            name = this.categoryName
        ),
        totalAmtSpent = this.totalAmtSpent,
    )
}