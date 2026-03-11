package com.zillotrix.moneytracker.features.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity
import com.zillotrix.moneytracker.features.budget.data.local.relation.BudgetWithCategoryAndExpensesRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity) : Long

    @Query("""
        SELECT * FROM budget
        WHERE monthYear = :monthYear
        ORDER BY name ASC
    """)
    fun getBudgetsForMonth(monthYear: Int): Flow<List<BudgetEntity>>

    @Transaction
    @Query("""
        SELECT 
        budget.*, 
        SUM(COALESCE(expense.amount, 0)) as totalAmtSpent 
        FROM budget
        LEFT JOIN expense ON budget.id = expense.budgetId 
            AND expense.date >= :startDate 
            AND expense.date <= :endDate
        WHERE budget.monthYear = :yearMonth
        GROUP BY budget.id
        ORDER BY budget.name ASC
    """)
    fun getBudgetsWithCategoryAndExpensesForMonth(
        yearMonth: Int,
        startDate: Long,
        endDate: Long
    ): Flow<List<BudgetWithCategoryAndExpensesRelation>>

    @Query("""
        SELECT * FROM budget
        WHERE id = :budgetId
    """)
    suspend fun getBudgetById(budgetId: Long): BudgetEntity?

    @Query("DELETE FROM budget WHERE id = :budgetId")
    suspend fun deleteBudget(budgetId: Long)
}