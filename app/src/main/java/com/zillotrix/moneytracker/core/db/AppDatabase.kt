package com.zillotrix.moneytracker.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zillotrix.moneytracker.core.db.dao.BudgetCategoryDao
import com.zillotrix.moneytracker.core.db.dao.BudgetDao
import com.zillotrix.moneytracker.core.db.dao.ExpenseDao
import com.zillotrix.moneytracker.core.db.dao.IncomeDao
import com.zillotrix.moneytracker.core.db.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.core.db.entity.ExpenseEntity
import com.zillotrix.moneytracker.core.db.entity.IncomeEntity
import com.zillotrix.moneytracker.core.db.entity.BudgetEntity

@Database(
    entities = [
        IncomeEntity::class,
        BudgetCategoryEntity::class,
        BudgetEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun budgetCategoryDao(): BudgetCategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao
}