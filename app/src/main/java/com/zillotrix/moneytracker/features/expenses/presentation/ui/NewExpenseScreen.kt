package com.zillotrix.moneytracker.features.expenses.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zillotrix.moneytracker.core.navigation.LocalNavActions
import com.zillotrix.moneytracker.core.utils.toDayMonthYearFull
import com.zillotrix.moneytracker.core.utils.toYearMonth
import com.zillotrix.moneytracker.features.expenses.presentation.ui.common.BudgetDropDownField
import com.zillotrix.moneytracker.features.expenses.presentation.ui.common.ExpenseDatePickerDialog
import com.zillotrix.moneytracker.features.expenses.presentation.view_model.NewExpenseViewModel
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExpenseScreen(newExpenseViewModel: NewExpenseViewModel = hiltViewModel<NewExpenseViewModel>()){
    val context = LocalContext.current
    val navActions = LocalNavActions.current
    val state by newExpenseViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            newExpenseViewModel.onError.collect { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        launch {
            newExpenseViewModel.onSuccess.collect { isSuccess ->
                if(isSuccess){
                    val msg = if (state.editMode) "Updated" else "Added"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Expense removed", Toast.LENGTH_SHORT).show()
                    navActions.navigateBack()
                }
            }
        }
    }

    if(state.showDatePickerDialog){
        ExpenseDatePickerDialog(
            selectedDate = state.date,
            yearMonth = state.yearMonth.toYearMonth(),
            onDateSelected = { millis ->
                newExpenseViewModel.onExpenseDateChanged(Date(millis))
            },
            onDismiss = {
                newExpenseViewModel.showDatePickerDialog(false)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if(state.editMode) "Update Expense" else "Add Expense") },
                navigationIcon = {
                    IconButton(onClick = { navActions.navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if(state.editMode){
                        IconButton(onClick = { newExpenseViewModel.deleteExpense() }) {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = "Delete Expense"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BudgetDropDownField(
                selectedBudget = state.selectedBudget,
                budgetList = state.budgetList,
                onBudgetChanged = {budget -> newExpenseViewModel.onBudgetChanged(budget)}
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = {value -> newExpenseViewModel.onExpenseNameChanged(value)},
                label = { Text("Name") },
                placeholder = { Text("e.g Groceries") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.amt,
                onValueChange = {value -> newExpenseViewModel.onExpenseAmtChanged(value)},
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Text("₹") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.date.toDayMonthYearFull(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Date") },
                trailingIcon = {
                    IconButton(
                        onClick = { newExpenseViewModel.showDatePickerDialog(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    //TODO: show loader
                    newExpenseViewModel.saveExpense()
                }
            ) {
                Text("Save")
            }
        }
    }
}