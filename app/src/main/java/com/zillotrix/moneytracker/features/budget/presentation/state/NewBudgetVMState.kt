package com.zillotrix.moneytracker.features.budget.presentation.state

import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory
import java.time.YearMonth

data class NewBudgetVMState(
    val name: String = "",
    val amt : String = "",
    val monthYear : Int = YearMonth.now().toIntYYYYMM(),
    val selectedBudgetCategory: BudgetCategory? = null,
    val budgetCategories: List<BudgetCategory> = emptyList(),
    val isLoading : Boolean = false,
    val showMonthPickerDialog: Boolean = false,
)
