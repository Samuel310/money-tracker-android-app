package com.zillotrix.moneytracker.features.budget.domain.repository

import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.budget.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun setBudget(budgetInfo: BudgetInfo) : RepoResult<Boolean, String>
    fun getAllBudgetByMonth(monthYear: Int): Flow<List<BudgetInfo>>
    suspend fun setCategory(name: String) : RepoResult<BudgetCategory, String>
    fun getAllCategories() : Flow<List<BudgetCategory>>
    suspend fun setIncome(income: Income)
    fun getMonthlyTotalIncome(monthYear: Int) : Flow<Long>
}