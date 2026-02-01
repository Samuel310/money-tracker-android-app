package com.zillotrix.moneytracker.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "income"
)
data class IncomeEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Long,
    val date: Long
)