package com.zillotrix.moneytracker.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zillotrix.moneytracker.core.db.entity.BudgetEntity
import com.zillotrix.moneytracker.core.db.relation.BudgetWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity)

    @Query("""
        SELECT * FROM budget
        WHERE monthYear = :monthYear
        ORDER BY name ASC
    """)
    fun getBudgetsForMonth(monthYear: Int): Flow<List<BudgetEntity>>

    @Transaction
    @Query("""
        SELECT * FROM budget
        WHERE monthYear = :monthYear
        ORDER BY name ASC
    """)
    fun getBudgetsWithCategory(
        monthYear: Int
    ): Flow<List<BudgetWithCategory>>

    @Query("""
        SELECT * FROM budget
        WHERE id = :budgetId
    """)
    suspend fun getBudgetById(budgetId: Long): BudgetEntity?

    @Query("DELETE FROM budget WHERE id = :budgetId")
    suspend fun deleteBudget(budgetId: Long)

    @Query("""
        UPDATE budget
        SET isSettled = :isSettled
        WHERE id = :budgetId
    """)
    suspend fun updateSettlement(
        budgetId: Long,
        isSettled: Boolean
    )
}