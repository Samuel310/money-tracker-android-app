package com.zillotrix.moneytracker.features.budget.domain.repository

import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.features.budget.domain.model.Budget
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetOverview
import com.zillotrix.moneytracker.features.budget.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun setBudget(budgetInfo: BudgetInfo) : RepoResult<Boolean, String>
    fun getBudgetInfoById(budgetId: Long, startDate: Long, endDate: Long) : RepoResult<Flow<BudgetInfo?>, String>
    suspend fun getBudgetById(budgetId: Long) : RepoResult<Budget?, String>
    fun getAllBudgetInfoByMonth(yearMonth: Int): RepoResult<Flow<List<BudgetInfo>>, String>
    fun getAllBudgetByMonth(yearMonth: Int): RepoResult<Flow<List<Budget>>, String>
    suspend fun setCategory(name: String) : RepoResult<BudgetCategory, String>
    fun getAllCategories() : Flow<List<BudgetCategory>>
    fun getBudgetOverview(yearMonth: Int) : RepoResult<Flow<BudgetOverview>, String>
    suspend fun setBudgetFromMostRecentBudget(yearMonth: Int) : RepoResult<Boolean, String>
    suspend fun setIncome(income: Income)
    fun getMonthlyTotalIncome(yearMonth: Int) : Flow<Long>
}