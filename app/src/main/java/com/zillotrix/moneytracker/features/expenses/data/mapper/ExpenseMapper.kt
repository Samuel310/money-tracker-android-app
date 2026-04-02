package com.zillotrix.moneytracker.features.expenses.data.mapper

import com.zillotrix.moneytracker.features.expenses.data.local.entity.ExpenseEntity
import com.zillotrix.moneytracker.features.expenses.domain.model.Expense
import java.util.Date

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = this.id,
        name = this.name,
        amount = this.amount,
        budgetId = this.budgetId,
        date = Date(this.date),
        budgetYearMonth = this.budgetYearMonth,
    )
}

fun Expense.toEntity() : ExpenseEntity{
    return ExpenseEntity(
        id = this.id,
        name = this.name,
        amount = this.amount,
        budgetId = this.budgetId,
        date = this.date.time,
        budgetYearMonth = this.budgetYearMonth
    )
}