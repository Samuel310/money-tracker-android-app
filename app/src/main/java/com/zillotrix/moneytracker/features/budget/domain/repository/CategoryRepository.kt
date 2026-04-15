package com.zillotrix.moneytracker.features.budget.domain.repository

import android.content.Context

interface CategoryRepository {
    suspend fun seedCategories(context: Context)
}