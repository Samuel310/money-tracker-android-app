package com.zillotrix.moneytracker.core.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zillotrix.moneytracker.features.budget.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class DatabaseSeeder(
    private val context: Context,
    private val categoryRepository: Provider<CategoryRepository>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            categoryRepository.get().seedCategories(context)
        }
    }
}