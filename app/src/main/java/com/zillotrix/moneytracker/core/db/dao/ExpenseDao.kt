package com.zillotrix.moneytracker.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zillotrix.moneytracker.core.db.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("""
        SELECT * FROM expense
        WHERE budgetId = :budgetId
        ORDER BY date DESC
    """)
    fun getExpensesForBudget(
        budgetId: Long
    ): Flow<List<ExpenseEntity>>

    @Query("""
        SELECT IFNULL(SUM(amount), 0)
        FROM expense
        WHERE budgetId = :budgetId
    """)
    fun getTotalExpenseForBudget(
        budgetId: Long
    ): Flow<Long>

    @Query("""
        SELECT * FROM expense
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    fun getExpensesBetween(
        startDate: Long,
        endDate: Long
    ): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteExpense(id: Long)
}