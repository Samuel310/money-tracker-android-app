package com.zillotrix.moneytracker.features.budget.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zillotrix.moneytracker.core.utils.RepoResult
import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo
import com.zillotrix.moneytracker.features.budget.domain.repository.BudgetRepository
import com.zillotrix.moneytracker.features.budget.presentation.state.BudgetScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class BudgetScreenViewModel @Inject constructor(private val budgetRepository: BudgetRepository)  : ViewModel(){
    private val _state = MutableStateFlow(BudgetScreenState())
    val state: StateFlow<BudgetScreenState> = _state

    private val _onError = MutableSharedFlow<String>()
    val onError = _onError.asSharedFlow()

    private var _observeJob: Job? = null

    init {
        observeAllBudgetInfo()
    }

    private fun observeAllBudgetInfo(){
        _observeJob?.cancel()
        _observeJob = viewModelScope.launch {
            when(val res = budgetRepository.getAllBudgetByMonth(_state.value.currentYearMonth.toIntYYYYMM())){
                is RepoResult.Success -> {
                    res.data.collect { budgetInfoList ->
                        val budgetInfoMap = mutableMapOf<String, List<BudgetInfo>>()
                        for (budgetInfo in budgetInfoList) {
                            val existingList = budgetInfoMap[budgetInfo.categoryName] ?: emptyList()
                            budgetInfoMap[budgetInfo.categoryName] = existingList + budgetInfo
                        }
                        _state.value = _state.value.copy(budgetInfoMap = budgetInfoMap, isLoading = false)
                    }
                }
                is RepoResult.Error -> {
                    _onError.emit(res.error)
                    _state.value = state.value.copy(isLoading = false)
                }
            }
        }
    }

    fun onMonthChanged(yearMonth: YearMonth){
        _observeJob?.cancel()
        _observeJob = viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true, currentYearMonth = yearMonth)
            delay(2000)
            observeAllBudgetInfo()
        }
    }

    fun showMonthPickerDialog(show: Boolean){
        _state.value = _state.value.copy(showMonthPickerDialog = show)
    }
}