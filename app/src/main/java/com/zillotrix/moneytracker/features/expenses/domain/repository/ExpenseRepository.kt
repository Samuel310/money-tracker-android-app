package com.zillotrix.moneytracker.features.expenses.domain.repository

import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.features.expenses.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface ExpenseRepository {
    suspend fun setExpense(expense: Expense) : RepoResult<Boolean, String>
    fun getExpensesForBudgetPerMonth(budgetId: Long, yearMonth: YearMonth) : RepoResult<Flow<List<Expense>>, String>
    suspend fun getExpenseById(expenseId: Long) : RepoResult<Expense?, String>
    suspend fun deleteExpense(expenseId: Long)
}