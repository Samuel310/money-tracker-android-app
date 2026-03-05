package com.zillotrix.moneytracker.features.budget.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "budget_category"
)
data class BudgetCategoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String
)