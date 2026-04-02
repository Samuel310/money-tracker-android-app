package com.zillotrix.moneytracker.features.expenses.presentation.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.core.utils.getMonthRange
import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.core.utils.toYearMonth
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import com.zillotrix.moneytracker.features.expenses.domain.repository.ExpenseRepository
import com.zillotrix.moneytracker.features.expenses.presentation.state.ExpenseScreenVMState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ExpenseScreenViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val budgetRepository: BudgetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val initialYearMonth = savedStateHandle.get<Int>("yearMonth") ?: YearMonth.now().toIntYYYYMM()
    private val initialBudgetId = savedStateHandle.get<Long>("budgetId") ?: 0L

    private val _state = MutableStateFlow(ExpenseScreenVMState().copy(yearMonth = initialYearMonth, budgetId = initialBudgetId))
    val state: StateFlow<ExpenseScreenVMState> = _state

    private val _onError = MutableSharedFlow<String>()
    val onError = _onError.asSharedFlow()

    init {
        observeAllExpenses()
        loadBudget()
    }

    private fun observeAllExpenses() {
        viewModelScope.launch {
            val res = expenseRepository.getExpensesForBudgetPerMonth(
                yearMonth = _state.value.yearMonth.toYearMonth(),
                budgetId = _state.value.budgetId,
            )
            when(res){
                is RepoResult.Success -> {
                    res.data.collect { expenseList ->
                        _state.value = _state.value.copy(expenseList = expenseList)
                    }
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                }
            }
        }
    }

    private fun loadBudget(){
        //TODO: enable loader
        viewModelScope.launch {
            val res = budgetRepository.getBudgetInfoById(
                budgetId = _state.value.budgetId,
            )
            when(res){
                is RepoResult.Success -> {
                    res.data.collect { budgetInfo ->
                        _state.value = _state.value.copy(budgetInfo = budgetInfo)
                    }
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                }
            }
        }
    }
}