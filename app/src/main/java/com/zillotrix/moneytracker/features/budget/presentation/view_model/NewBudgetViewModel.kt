package com.zillotrix.moneytracker.features.budget.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import com.zillotrix.moneytracker.features.budget.presentation.state.NewBudgetVMState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject


@HiltViewModel
class NewBudgetViewModel @Inject constructor(private val budgetRepository: BudgetRepository)  : ViewModel()  {

    private val _state = MutableStateFlow(NewBudgetVMState())
    val state: StateFlow<NewBudgetVMState> = _state

    private val _onError = MutableSharedFlow<String>()
    val onError = _onError.asSharedFlow()

    private val _onSuccess = MutableSharedFlow<Boolean>()
    val onSuccess = _onSuccess.asSharedFlow()

    init {
        observeAllBudgetCategories()
    }

    private fun observeAllBudgetCategories(){
        viewModelScope.launch {
            budgetRepository.getAllCategories().collect { categories -> _state.value = _state.value.copy(budgetCategories = categories) }
        }
    }

    fun addCategory(name: String){
        viewModelScope.launch {
            when(val res = budgetRepository.setCategory(name)){
                is RepoResult.Success -> {
                    _state.value = _state.value.copy(selectedBudgetCategory = res.data)
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                }
            }
        }
    }

    fun onCategoryChanged(budgetCategory: BudgetCategory){
        _state.value = _state.value.copy(selectedBudgetCategory = budgetCategory)
    }

    fun onBudgetNameChanged(name: String){
        _state.value = _state.value.copy(name = name)
    }

    fun onAmtChanged(amt: String){
        _state.value = _state.value.copy(amt = amt)
    }

    fun onMontYearChanged(monthYear: YearMonth){
        _state.value = _state.value.copy(monthYear = monthYear.toIntYYYYMM())
    }

    fun showMonthPickerDialog(show: Boolean){
        _state.value = _state.value.copy(showMonthPickerDialog = show)
    }

    fun setBudget(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when(val res = budgetRepository.setBudget(
                BudgetInfo(
                    0L,
                    name = _state.value.name,
                    amount = if (_state.value.amt.isEmpty()) 0L else _state.value.amt.toLong(),
                    categoryId = _state.value.selectedBudgetCategory?.id ?: 0L,
                    monthYear = _state.value.monthYear,
                    categoryName = _state.value.selectedBudgetCategory?.name ?: "",
                )
            )){
                is RepoResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _onSuccess.emit(true)
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                }
            }
        }
    }

}