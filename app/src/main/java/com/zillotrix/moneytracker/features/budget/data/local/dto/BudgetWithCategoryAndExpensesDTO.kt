package com.zillotrix.moneytracker.features.budget.data.local.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity

data class BudgetWithCategoryAndExpensesDTO(
    @Embedded val budget: BudgetEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: BudgetCategoryEntity,
    val totalAmtSpent: Long
)