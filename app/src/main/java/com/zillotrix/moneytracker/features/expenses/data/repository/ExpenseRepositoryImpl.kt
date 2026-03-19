package com.zillotrix.moneytracker.features.expenses.data.repository

import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.core.utils.getMonthRange
import com.zillotrix.moneytracker.features.expenses.data.local.dao.ExpenseDao
import com.zillotrix.moneytracker.features.expenses.data.mapper.toDomain
import com.zillotrix.moneytracker.features.expenses.data.mapper.toEntity
import com.zillotrix.moneytracker.features.expenses.domain.model.Expense
import com.zillotrix.moneytracker.features.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.YearMonth
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun setExpense(expense: Expense): RepoResult<Boolean, String> {
        try {
            if(expense.amount <= 0){
                return RepoResult.Error("Amount cannot be smaller than 0")
            }
            if(expense.name.isEmpty()){
                return RepoResult.Error("Name cannot be empty")
            }
            if(expense.budgetId <= 0){
                return RepoResult.Error("Select a valid budget")
            }
            expenseDao.insertExpense(expense.toEntity())
            return RepoResult.Success(true)
        }catch (e : Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable save expense")
        }
    }

    override fun getExpensesForBudgetPerMonth(budgetId: Long, yearMonth: YearMonth): RepoResult<Flow<List<Expense>>, String> {
        try {
            val (startDate, endDate) = yearMonth.getMonthRange()
            val res = expenseDao.getExpensesForBudgetBetween(
                startDate = startDate,
                endDate = endDate,
                budgetId = budgetId,
            ).map { expenseEntityList ->
                expenseEntityList.map { expenseEntity -> expenseEntity.toDomain() }
            }
            return RepoResult.Success(res)
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to load budget info")
        }
    }

    override suspend fun getExpenseById(expenseId: Long): RepoResult<Expense?, String> {
        try {
            val expenseEntity = expenseDao.getExpenseById(expenseId)
            return RepoResult.Success(expenseEntity?.toDomain())
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to get Expense data")
        }
    }

    override suspend fun deleteExpense(expenseId: Long) {
        TODO("Not yet implemented")
    }
}