package com.zillotrix.moneytracker.features.budget.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme
import com.zillotrix.moneytracker.core.utils.toYearMonth
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.CategoryDropdownField
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.MonthPickerDialog
import com.zillotrix.moneytracker.features.budget.presentation.view_model.NewBudgetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBudgetScreen(navigateBack: () -> Unit, newBudgetViewModel: NewBudgetViewModel = hiltViewModel<NewBudgetViewModel>()) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val state by newBudgetViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            newBudgetViewModel.onError.collect { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        launch {
            newBudgetViewModel.onSuccess.collect { isSuccess ->
                if(isSuccess){
                    navigateBack()
                }
            }
        }
    }

    if(state.showMonthPickerDialog){
        MonthPickerDialog(
            selectedYearMonth = state.monthYear.toYearMonth(),
            onDismiss = { monthYear ->
                if(monthYear != null){
                    newBudgetViewModel.onMontYearChanged(monthYear = monthYear)
                }
                newBudgetViewModel.showMonthPickerDialog(show = false)
                focusManager.clearFocus(true)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Budget") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
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

            CategoryDropdownField(
                selectedBudgetCategory = state.selectedBudgetCategory,
                budgetCategories = state.budgetCategories,
                onCategoryChanged = { selectedBudgetCategory -> newBudgetViewModel.onCategoryChanged(selectedBudgetCategory)}
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = {value -> newBudgetViewModel.onBudgetNameChanged(value)},
                label = { Text("Budget Name") },
                placeholder = { Text("e.g. Monthly Groceries") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.amt,
                onValueChange = {value -> newBudgetViewModel.onAmtChanged(value)},
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Text("₹") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().onFocusChanged(onFocusChanged = {focusState ->
                    if (focusState.isFocused) {
                        newBudgetViewModel.showMonthPickerDialog(show = true)
                    } else {
                        newBudgetViewModel.showMonthPickerDialog(show = false)
                    }
                }),
                value = state.monthYear.toYearMonth().toString(),
                onValueChange = {},
                label = { Text("Year - Month") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        newBudgetViewModel.showMonthPickerDialog(show = true)
                    }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Pick Month"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    newBudgetViewModel.setBudget()
                }
            ) {
                Text("Save")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNewBudgetScreen(){
    MoneyTrackerTheme{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            NewBudgetScreen(navigateBack = {})
        }
    }
}