package com.zillotrix.moneytracker.features.expenses.presentation.state

import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.features.budget.domain.model.Budget
import java.time.YearMonth
import java.util.Date

data class NewExpenseVMState(
    val name: String = "",
    val amt : String = "",
    val selectedBudget: Budget? = null,
    val budgetList: List<Budget> = emptyList(),
    val date: Date? = Date(),
    val yearMonth: Int = YearMonth.now().toIntYYYYMM(),
    val isLoading : Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val editMode: Boolean = false,
    val expenseId: Long = 0L,
)
