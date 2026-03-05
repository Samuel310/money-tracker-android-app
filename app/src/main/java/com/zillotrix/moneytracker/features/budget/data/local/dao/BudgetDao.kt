package com.zillotrix.moneytracker.features.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetEntity
import com.zillotrix.moneytracker.features.budget.data.local.relation.BudgetWithCategoryRelation
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
        SELECT * FROM budget
        WHERE monthYear = :monthYear
        ORDER BY name ASC
    """)
    fun getBudgetsWithCategoryForMonth(
        monthYear: Int
    ): Flow<List<BudgetWithCategoryRelation>>

    @Query("""
        SELECT * FROM budget
        WHERE id = :budgetId
    """)
    suspend fun getBudgetById(budgetId: Long): BudgetEntity?

    @Query("DELETE FROM budget WHERE id = :budgetId")
    suspend fun deleteBudget(budgetId: Long)
}