package com.zillotrix.moneytracker.features.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity
import com.zillotrix.moneytracker.features.budget.data.local.dto.BudgetWithCategoryAndExpensesDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgets(budgets: List<BudgetEntity>)

    @Query("""
        SELECT * FROM budget
        WHERE yearMonth = :yearMonth
        ORDER BY name ASC
    """)
    fun getBudgetsForMonth(yearMonth: Int): Flow<List<BudgetEntity>>

    @Query("""
        SELECT * FROM budget
        WHERE yearMonth = :yearMonth
        ORDER BY name ASC
    """)
    suspend fun getBudgetsForMonthSnapshot(yearMonth: Int): List<BudgetEntity>

    @Transaction
    @Query("""
        SELECT 
        budget.*, 
        SUM(COALESCE(expense.amount, 0)) as totalAmtSpent 
        FROM budget
        LEFT JOIN expense ON budget.id = expense.budgetId 
            AND expense.date >= :startDate 
            AND expense.date <= :endDate
        WHERE budget.yearMonth = :yearMonth
        GROUP BY budget.id
        ORDER BY budget.name ASC
    """)
    fun getBudgetsWithCategoryAndExpensesForMonth(
        yearMonth: Int,
        startDate: Long,
        endDate: Long
    ): Flow<List<BudgetWithCategoryAndExpensesDTO>>

    @Query("""
        SELECT * FROM budget
        WHERE id = :budgetId
    """)
    suspend fun getBudgetById(budgetId: Long): BudgetEntity?

    @Transaction
    @Query("""
        SELECT 
        budget.*,
        SUM(COALESCE(expense.amount, 0)) as totalAmtSpent 
        FROM budget
        LEFT JOIN expense ON budget.id = expense.budgetId 
            AND expense.date >= :startDate 
            AND expense.date <= :endDate
        WHERE budget.id = :budgetId
        GROUP BY budget.id
    """)
    fun getBudgetWithCategoryAndExpensesById(
        budgetId: Long,
        startDate: Long,
        endDate: Long
    ) : Flow<BudgetWithCategoryAndExpensesDTO?>

    @Query("SELECT MAX(yearMonth) FROM budget WHERE yearMonth < :targetYearMonth")
    suspend fun getMostRecentBudgetMonth(targetYearMonth: Int): Int?

    @Query("DELETE FROM budget WHERE id = :budgetId")
    suspend fun deleteBudget(budgetId: Long)

    @Query("""
        SELECT SUM(amount) FROM budget WHERE yearMonth = :yearMonth
        """)
    fun getTotalBudget(yearMonth: Int): Flow<Long?>
}