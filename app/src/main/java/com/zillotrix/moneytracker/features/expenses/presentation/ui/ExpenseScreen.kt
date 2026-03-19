package com.zillotrix.moneytracker.features.expenses.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zillotrix.moneytracker.core.navigation.LocalNavActions
import com.zillotrix.moneytracker.core.utils.toShortMonthYear
import com.zillotrix.moneytracker.core.utils.toYearMonth
import com.zillotrix.moneytracker.features.expenses.presentation.ui.common.ExpenseItem
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.BudgetInfoCard
import com.zillotrix.moneytracker.features.expenses.presentation.view_model.ExpenseScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(
    expenseScreenViewModel: ExpenseScreenViewModel = hiltViewModel<ExpenseScreenViewModel>()
){
    val context = LocalContext.current
    val navActions = LocalNavActions.current
    val state by expenseScreenViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            expenseScreenViewModel.onError.collect { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${state.budgetInfo?.name} — ${state.budgetInfo?.yearMonth?.toYearMonth()?.toShortMonthYear()}") },
                navigationIcon = {
                    IconButton(onClick = { navActions.navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navActions.navigateToNewExpenseScreen(
                    budgetId = state.budgetId,
                    yearMonth = state.yearMonth,
                    expenseId = null,
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Expense"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding),
        ) {
            BudgetInfoCard(
                budgetInfo = state.budgetInfo,
                clickable = false,
                shadowElevation = 4.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(state.expenseList.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.expenseList,
                        key = { it.id }
                    ) { expense ->
                        ExpenseItem(expense = expense, onNavigateToNewExpenseScreen = {
                            navActions.navigateToNewExpenseScreen(
                                budgetId = state.budgetId,
                                yearMonth = state.yearMonth,
                                expenseId = expense.id,
                            )
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //TODO: show empty list view.
        }
    }

}