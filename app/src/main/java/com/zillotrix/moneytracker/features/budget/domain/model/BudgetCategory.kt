package com.zillotrix.moneytracker.features.budget.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class BudgetCategory(
    val id: Long,
    val name: String,
    val info: String,
    val icon: ImageVector,
    val color: Color,
    val isDefault: Boolean,
)
