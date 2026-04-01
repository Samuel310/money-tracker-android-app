package com.zillotrix.moneytracker.features.budget.presentation.state

import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetOverview
import java.time.YearMonth

data class BudgetScreenState(
    val budgetInfoMap: Map<String, List<BudgetInfo>> = emptyMap(),
    val budgetOverview: BudgetOverview? = null,
    val currentYearMonth: YearMonth = YearMonth.now(),
    val isBudgetOverviewLoading: Boolean = false,
    val isBudgetInfoMapLoading: Boolean = false,
    val showMonthPickerDialog: Boolean = false,
    val isFabExpanded: Boolean = false,
)
