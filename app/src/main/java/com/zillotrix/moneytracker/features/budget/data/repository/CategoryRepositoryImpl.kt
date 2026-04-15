package com.zillotrix.moneytracker.features.budget.data.repository

import android.content.Context
import android.util.Log
import com.zillotrix.moneytracker.features.budget.data.local.dao.BudgetCategoryDao
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.domain.repository.CategoryRepository
import jakarta.inject.Inject
import org.json.JSONObject

class CategoryRepositoryImpl @Inject constructor(
    private val budgetCategoryDao: BudgetCategoryDao,
) : CategoryRepository{

    override suspend fun seedCategories(context: Context) {
        try {
            val jsonString = context.assets.open("category.json")
                .bufferedReader()
                .use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val categoriesArray = jsonObject.getJSONArray("categories")
            val categoriesToInsert = mutableListOf<BudgetCategoryEntity>()
            for (i in 0 until categoriesArray.length()) {
                val item = categoriesArray.getJSONObject(i)
                categoriesToInsert.add(
                    BudgetCategoryEntity(
                        name = item.getString("name"),
                        info = item.getString("info"),
                        id = item.getLong("id"),
                        icon = item.getString("icon"),
                        color = item.getString("color"),
                        isDefault = item.getBoolean("isDefault"),
                    )
                )
            }
            budgetCategoryDao.upsertCategories(categoriesToInsert)
        }catch (e : Exception){
            Log.e("DatabaseSeeder", "Error seeding categories", e)
        }
    }

}