package com.zillotrix.moneytracker.core.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.zillotrix.moneytracker.core.db.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.core.db.entity.BudgetEntity

data class BudgetWithCategory(
    @Embedded val budget: BudgetEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: BudgetCategoryEntity
)