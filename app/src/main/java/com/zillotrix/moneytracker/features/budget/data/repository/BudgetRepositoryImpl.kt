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
import com.zillotrix.moneytracker.features.budget.domain.model.Income
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao,
    private val categoryDao: BudgetCategoryDao,
    private val incomeDao: IncomeDao,
) : BudgetRepository {

    override suspend fun setBudget(budgetInfo: BudgetInfo) : RepoResult<Boolean, String> {
        try{
            if(budgetInfo.name.isEmpty()){
                return RepoResult.Error("Budget name cannot be empty")
            }
            if(budgetInfo.amount < 0){
                return RepoResult.Error("Budget amt. cannot be smaller that 0")
            }
            if(budgetInfo.monthYear <= 0){
                return RepoResult.Error("Select a valid month")
            }
            if(budgetInfo.categoryId < 0){
                return RepoResult.Error("Select a valid category")
            }
            if(budgetInfo.categoryName.isEmpty()){
                return RepoResult.Error("Select a valid category")
            }
            val budgetCategory = BudgetCategory(
                id = budgetInfo.categoryId,
                name = budgetInfo.categoryName
            )
            var categoryId = categoryDao.insertCategory(budgetCategory.toEntity())
            if(categoryId <= 0){
                categoryId = budgetInfo.categoryId
            }
            val budget = Budget(
                id = budgetInfo.id,
                name = budgetInfo.name,
                amount = budgetInfo.amount,
                categoryId = categoryId,
                monthYear = budgetInfo.monthYear,
            )
            budgetDao.insertBudget(budget.toEntity())
            return RepoResult.Success(true)
        }catch (e: Exception){
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to create budget")
        }

    }

    override fun getAllBudgetByMonth(yearMonth: Int): RepoResult<Flow<List<BudgetInfo>>, String> {
        try {
            val res = budgetDao.getBudgetsWithCategoryForMonth(yearMonth).map { budgetWithCategoryRelationList ->
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

    override suspend fun setCategory(name: String) : RepoResult<BudgetCategory, String>  {
        try {
            if(name.isEmpty()){
                return RepoResult.Error("Category name cannot be empty")
            }
            val id = categoryDao.insertCategory(BudgetCategoryEntity(name = name))
            if(id <= 0){
                return RepoResult.Error("Category name already exists")
            }
            return RepoResult.Success(BudgetCategory(id, name))
        }catch (e: Exception) {
            //TODO: implement logger
            return RepoResult.Error("Something went wrong, Unable to create category")
        }
    }

    override fun getAllCategories(): Flow<List<BudgetCategory>> {
        return categoryDao.getAllCategories().map { budgetCategoryEntitiesList ->
            budgetCategoryEntitiesList.map { budgetCategoryEntity ->
                budgetCategoryEntity.toDomain()
            }
        }
    }

    override suspend fun setIncome(income: Income) {
        incomeDao.insertIncome(income.toEntity())
    }

    override fun getMonthlyTotalIncome(monthYear: Int): Flow<Long> {
        TODO("Not yet implemented")
    }
}