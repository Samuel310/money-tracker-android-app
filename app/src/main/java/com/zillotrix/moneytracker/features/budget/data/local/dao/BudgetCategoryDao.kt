package com.zillotrix.moneytracker.features.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.data.local.dto.CategoryTotalDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: BudgetCategoryEntity) : Long

    @Query("SELECT * FROM budget_category ORDER BY name ASC")
    fun getAllCategories(): Flow<List<BudgetCategoryEntity>>

    @Query("DELETE FROM budget_category WHERE id = :id")
    suspend fun deleteCategory(id: Long)

    @Query("""
        SELECT bc.id as categoryId, bc.name as categoryName, SUM(b.amount) as totalAmount
        FROM budget b
        INNER JOIN budget_category bc
        ON b.categoryId = bc.id
        WHERE b.yearMonth = :yearMonth
        GROUP BY bc.id, bc.name
    """)
    fun getCategoryTotals(yearMonth: Int): Flow<List<CategoryTotalDTO>>
}