package com.zillotrix.moneytracker.features.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.data.local.dto.CategoryTotalDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetCategoryDao {

    @Upsert
    suspend fun upsertCategories(categories: List<BudgetCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: BudgetCategoryEntity) : Long

    @Query("SELECT * FROM budget_category ORDER BY name ASC")
    fun getAllCategories(): Flow<List<BudgetCategoryEntity>>

    @Query("DELETE FROM budget_category WHERE id = :id")
    suspend fun deleteCategory(id: Long)

    @Query("""
        SELECT bc.*, SUM(b.amount) as totalPlannedAmount
        FROM budget_category bc
        INNER JOIN budget b
        ON bc.id = b.categoryId
        WHERE b.yearMonth = :yearMonth
        GROUP BY bc.id
    """)
    fun getAllCategoryWisePlannedTotalAmt(yearMonth: Int): Flow<List<CategoryTotalDTO>>
}