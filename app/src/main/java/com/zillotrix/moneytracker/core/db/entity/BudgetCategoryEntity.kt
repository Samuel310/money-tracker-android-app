package com.zillotrix.moneytracker.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "budget_category"
)
class BudgetCategoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String
)