package com.zillotrix.moneytracker.features.budget.data.repository

import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.features.budget.data.local.dao.BudgetCategoryDao
import com.zillotrix.moneytracker.features.budget.data.local.dao.BudgetDao
import com.zillotrix.moneytracker.features.budget.data.local.dao.IncomeDao
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.data.mapper.toDomain
import com.zillotrix.moneytracker.features.budget.data.mapper.toEntity
import com.zillotrix.moneytracker.features.budget.domain.model.Budget
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetOverview
import com.zillotrix.moneytracker.features.budget.domain.model.Income
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import com.zillotrix.moneytracker.features.expenses.data.local.dao.ExpenseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map
import kotlin.math.round

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao,
    private val categoryDao: BudgetCategoryDao,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
) : BudgetRepository {

    override suspend fun setBudget(budget: Budget, budgetCategory: BudgetCategory?) : RepoResult<Boolean, String> {
        try{
            if(budget.name.isEmpty()){
                return RepoResult.Error("Budget name cannot be empty")
            }
            if(budget.amount <= 0){
                return RepoResult.Error("Enter a valid budget amount")
            }
            if(budget.yearMonth <= 0){
                return RepoResult.Error("Select a valid month")
            }
            if(budget.categoryId < 0){
                return RepoResult.Error("Select a valid category")
            }
            if(budgetCategory == null || budgetCategory.name.isEmpty() || budgetCategory.id < 0){
                return RepoResult.Error("Select a valid category")
            }
            var categoryId = categoryDao.insertCategory(budgetCategory.toEntity())
            if(categoryId <= 0){
                categoryId = budget.categoryId
            }
            val newBudget = budget.copy(categoryId = categoryId)
            budgetDao.insertBudget(newBudget.toEntity())
            return RepoResult.Success(true)
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to create budget")
        }

    }

    override fun getBudgetInfoById(budgetId: Long): RepoResult<Flow<BudgetInfo?>, String> {
        try {
            val res = budgetDao.getBudgetWithCategoryAndExpensesById(
               budgetId = budgetId,
            ).map { value -> value?.toDomain() }
            return RepoResult.Success(res)
        }catch (e : Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to load budget info")
        }
    }

    override suspend fun getBudgetById(budgetId: Long): RepoResult<Budget?, String> {
        try {
            val res = budgetDao.getBudgetById(budgetId = budgetId)
            return if(res == null){
                RepoResult.Error("Budget not found")
            }else{
                RepoResult.Success(res.toDomain())
            }
        }catch (e : Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to load budget")
        }
    }

    override fun getAllBudgetInfoByMonth(yearMonth: Int): RepoResult<Flow<List<BudgetInfo>>, String> {
        try {
            val res = budgetDao.getBudgetsWithCategoryAndExpensesForMonth(
                yearMonth = yearMonth,
            ).map { budgetWithCategoryRelationList ->
                budgetWithCategoryRelationList.map { budgetWithCategoryRelation ->
                    budgetWithCategoryRelation.toDomain()
                }
            }
            return RepoResult.Success(res)
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to load budget info")
        }
    }

    override fun getAllBudgetByMonth(yearMonth: Int): RepoResult<Flow<List<Budget>>, String> {
        try {
            val res = budgetDao.getBudgetsForMonth(
                yearMonth = yearMonth,
            ).map { budgetEntityList ->
                budgetEntityList.map { budgetEntity ->
                    budgetEntity.toDomain()
                }
            }
            return RepoResult.Success(res)
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to load budget info")
        }
    }

    override fun getAllCategories(): Flow<List<BudgetCategory>> {
        return categoryDao.getAllCategories().map { budgetCategoryEntitiesList ->
            budgetCategoryEntitiesList.map { budgetCategoryEntity ->
                budgetCategoryEntity.toDomain()
            }
        }
    }

    override fun getBudgetOverview(yearMonth: Int) : RepoResult<Flow<BudgetOverview>, String> {
        try {
            val res = combine(
                budgetDao.getTotalBudget(yearMonth),
                categoryDao.getAllCategoryWisePlannedTotalAmt(yearMonth),
                expenseDao.getMonthlyTotalExpenseAmt(yearMonth),
            ){ total , categories, totalSpentAmt ->
                val safeTotal = total ?: 0L
                val categoryMap = categories.associateBy(
                    keySelector = { it.budgetCategoryEntity.name },
                    valueTransform = { categoryTotalEntity ->
                        val percentage = if (safeTotal == 0L) 0.0 else (categoryTotalEntity.totalPlannedAmount.toDouble() / safeTotal) * 100
                        val roundPercentage = round(percentage * 100) / 100.0
                        categoryTotalEntity.toDomain(roundPercentage)
                    }
                )
                BudgetOverview(
                    yearMonth = yearMonth,
                    totalBudget = safeTotal,
                    categories = categoryMap,
                    totalSpentAmt = totalSpentAmt,
                    totalAvailableAmt = safeTotal - totalSpentAmt,
                )
            }
            return RepoResult.Success(res)
        }catch (e : Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to retrieve budget overview")
        }
    }

    override suspend fun setBudgetFromMostRecentBudget(yearMonth: Int): RepoResult<Boolean, String> {
        try {
            val mostRecentMonth = budgetDao.getMostRecentBudgetMonth(targetYearMonth = yearMonth)
                ?: return RepoResult.Error("No previous budget found")
            val existingBudgets = budgetDao.getBudgetsForMonthSnapshot(yearMonth = mostRecentMonth)
            val newBudgets = existingBudgets.map { budget ->
                budget.copy(
                    id = 0L,
                    yearMonth = yearMonth
                )
            }
            budgetDao.insertBudgets(newBudgets)
            return RepoResult.Success(true)
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to create budget")
        }
    }

    override suspend fun setIncome(income: Income) {
        incomeDao.insertIncome(income.toEntity())
    }

    override fun getMonthlyTotalIncome(yearMonth: Int): Flow<Long> {
        TODO("Not yet implemented")
    }
}