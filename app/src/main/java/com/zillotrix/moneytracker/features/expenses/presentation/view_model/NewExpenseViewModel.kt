package com.zillotrix.moneytracker.features.expenses.presentation.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.features.budget.domain.model.Budget
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import com.zillotrix.moneytracker.features.expenses.domain.model.Expense
import com.zillotrix.moneytracker.features.expenses.domain.repository.ExpenseRepository
import com.zillotrix.moneytracker.features.expenses.presentation.state.NewExpenseVMState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.time.YearMonth
import javax.inject.Inject


@HiltViewModel
class NewExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val budgetRepository: BudgetRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialYearMonth = savedStateHandle.get<Int>("yearMonth") ?: YearMonth.now().toIntYYYYMM()

    private val _state = MutableStateFlow(NewExpenseVMState().copy(yearMonth = initialYearMonth))
    val state: StateFlow<NewExpenseVMState> = _state

    private val _onError = MutableSharedFlow<String>()
    val onError = _onError.asSharedFlow()

    private val _onSuccess = MutableSharedFlow<Boolean>()
    val onSuccess = _onSuccess.asSharedFlow()

    init {
        observeAllBudget()
    }

    private fun observeAllBudget(){
        viewModelScope.launch {
            val expenseId = savedStateHandle.get<Long>("expenseId") ?: 0L
            val initialBudgetId = savedStateHandle.get<Long>("budgetId") ?: 0L

            if(expenseId > 0L){
                _state.value = _state.value.copy(isLoading = true)
                when(val res = expenseRepository.getExpenseById(expenseId)){
                    is RepoResult.Success -> {
                        if(res.data == null || res.data.budgetId != initialBudgetId){
                            _onError.emit("Invalid Budget found")
                            return@launch
                        }
                        _state.value = _state.value.copy(
                            name = res.data.name,
                            amt = res.data.amount.toString(),
                            date = res.data.date,
                            editMode = true,
                            expenseId = res.data.id,
                            isLoading = false
                        )
                    }
                    is RepoResult.Error -> {
                        _onError.emit(res.error)
                        _state.value = _state.value.copy(isLoading = false)
                    }
                }
            }

            val res = budgetRepository.getAllBudgetByMonth(yearMonth = _state.value.yearMonth)
            when(res){
                is RepoResult.Success -> {
                    res.data.collect { budgetList ->
                        val selectedBudget = if(initialBudgetId > 0L){
                            budgetList.find { it.id == initialBudgetId } ?: budgetList.first()
                        }else{
                            budgetList.first()
                        }
                        _state.value = _state.value.copy(budgetList = budgetList, selectedBudget = selectedBudget)
                    }
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                }
            }
        }
    }

    fun onBudgetChanged(budget: Budget){
        _state.value = _state.value.copy(selectedBudget = budget)
    }

    fun onExpenseNameChanged(name: String){
        _state.value = _state.value.copy(name = name)
    }

    fun onExpenseAmtChanged(amt: String){
        _state.value = _state.value.copy(amt = amt)
    }

    fun onExpenseDateChanged(date: Date){
        _state.value = _state.value.copy(date = date)
    }

    fun showDatePickerDialog(value: Boolean){
        _state.value = _state.value.copy(showDatePickerDialog = value)
    }

    fun saveExpense(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val res = expenseRepository.setExpense(
                expense = Expense(
                    id = _state.value.expenseId,
                    name = _state.value.name,
                    amount = if (_state.value.amt.isEmpty()) 0L else _state.value.amt.toLong(),
                    budgetId = _state.value.selectedBudget?.id ?: 0L,
                    date = _state.value.date,
                )
            )
            when(res){
                is RepoResult.Success -> {
                    _onSuccess.emit(true)
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                }
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun deleteExpense(){
        viewModelScope.launch{
            if(_state.value.expenseId > 0L && _state.value.editMode){
                when(val res = expenseRepository.deleteExpense(_state.value.expenseId)){
                    is RepoResult.Success -> {
                        _onSuccess.emit(false)
                    }
                    is RepoResult.Error -> {
                        _onError.emit(res.error)
                    }
                }
            }
        }
    }
}