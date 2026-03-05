package com.zillotrix.moneytracker.features.budget.presentation.state

import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import java.time.YearMonth

data class BudgetScreenState(
    val budgetInfoMap: Map<String, List<BudgetInfo>> = emptyMap(),
    val currentYearMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = false,
    val showMonthPickerDialog: Boolean = false,
)
