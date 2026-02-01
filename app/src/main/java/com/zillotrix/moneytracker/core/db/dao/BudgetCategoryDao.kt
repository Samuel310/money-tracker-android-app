package com.zillotrix.moneytracker.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zillotrix.moneytracker.core.db.entity.BudgetCategoryEntity

@Dao
interface BudgetCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: BudgetCategoryEntity)

    @Query("SELECT * FROM budget_category ORDER BY name ASC")
    suspend fun getAllCategories(): List<BudgetCategoryEntity>

    @Query("DELETE FROM budget_category WHERE id = :id")
    suspend fun deleteCategory(id: Long)
}