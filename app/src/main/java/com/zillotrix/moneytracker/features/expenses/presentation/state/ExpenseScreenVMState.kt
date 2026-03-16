package com.zillotrix.moneytracker.features.expenses.presentation.state

import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.expenses.domain.model.Expense
import java.time.YearMonth

data class ExpenseScreenVMState(
    val expenseList: List<Expense> = emptyList(),
    val yearMonth: Int = YearMonth.now().toIntYYYYMM(),
    val budgetId: Long = 0L,
    val budgetInfo: BudgetInfo? = null,
    val isLoading: Boolean = false,
)
