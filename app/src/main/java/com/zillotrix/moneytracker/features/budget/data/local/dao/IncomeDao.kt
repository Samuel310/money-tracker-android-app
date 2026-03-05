package com.zillotrix.moneytracker.features.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zillotrix.moneytracker.features.budget.data.local.entity.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeEntity)

    @Query("""
        SELECT * FROM income
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    fun getIncomeBetween(
        startDate: Long,
        endDate: Long
    ): Flow<List<IncomeEntity>>

    @Query("""
        SELECT IFNULL(SUM(amount), 0)
        FROM income
        WHERE date BETWEEN :startDate AND :endDate
    """)
    fun getTotalIncomeBetween(
        startDate: Long,
        endDate: Long
    ): Flow<Long>

    @Query("DELETE FROM income WHERE id = :id")
    suspend fun deleteIncome(id: Long)
}