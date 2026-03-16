package com.zillotrix.moneytracker.core.di

import com.zillotrix.moneytracker.features.budget.data.repository.BudgetRepositoryImpl
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import com.zillotrix.moneytracker.features.expenses.data.repository.ExpenseRepositoryImpl
import com.zillotrix.moneytracker.features.expenses.domain.repository.ExpenseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBudgetRepository(
        impl: BudgetRepositoryImpl
    ): BudgetRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        impl: ExpenseRepositoryImpl
    ): ExpenseRepository
}