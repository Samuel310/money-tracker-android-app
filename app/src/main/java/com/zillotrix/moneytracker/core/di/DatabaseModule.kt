package com.zillotrix.moneytracker.core.di

import android.content.Context
import androidx.room.Room
import com.zillotrix.moneytracker.core.db.AppDatabase
import com.zillotrix.moneytracker.features.budget.data.local.dao.BudgetCategoryDao
import com.zillotrix.moneytracker.features.budget.data.local.dao.BudgetDao
import com.zillotrix.moneytracker.core.db.dao.ExpenseDao
import com.zillotrix.moneytracker.features.budget.data.local.dao.IncomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ) : AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shopping-app-db"
        ).build()
    }

    @Provides
    fun provideIncomeDao(
        db: AppDatabase
    ): IncomeDao = db.incomeDao()

    @Provides
    fun provideBudgetCategoryDao(
        db: AppDatabase
    ): BudgetCategoryDao = db.budgetCategoryDao()

    @Provides
    fun provideBudgetDao(
        db: AppDatabase
    ): BudgetDao = db.budgetDao()

    @Provides
    fun provideExpenseDao(
        db: AppDatabase
    ): ExpenseDao = db.expenseDao()

}