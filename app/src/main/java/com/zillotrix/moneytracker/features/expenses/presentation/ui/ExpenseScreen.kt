package com.zillotrix.moneytracker.features.expenses.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zillotrix.moneytracker.features.expenses.presentation.ui.common.ExpenseItem
import com.zillotrix.moneytracker.features.expenses.presentation.view_model.ExpenseScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(
    navigateBack: () -> Unit,
    onNavigateToNewExpenseScreen: (budgetId: Long, yearMonth: Int) -> Unit,
    expenseScreenViewModel: ExpenseScreenViewModel = hiltViewModel<ExpenseScreenViewModel>()
){
    val context = LocalContext.current
    val state by expenseScreenViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            expenseScreenViewModel.onError.collect { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val plannedAmt = state.budgetInfo?.amount ?: 0L
    val totalAmtSpent = state.budgetInfo?.totalAmtSpent ?: 0L
    val remainingAmount = plannedAmt - totalAmtSpent
    val progress = if (plannedAmt > 0L) {
        (totalAmtSpent / plannedAmt.toFloat()).coerceIn(0f, 1f)
    } else 0f



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${state.budgetInfo?.name} Expenses") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
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
                onNavigateToNewExpenseScreen(state.budgetId, state.yearMonth)
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
                .padding(innerPadding)
        ) {
            //TODO: show loader
            Text(
                text = "Planned Budget ₹${plannedAmt}",
            )
            Text(
                text = "Total Spent ₹${totalAmtSpent}",
            )
            Text(
                text = "Available ₹${remainingAmount}",
            )
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                strokeCap = StrokeCap.Round,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = if (progress >= 1f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )

            if(state.expenseList.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.expenseList,
                        key = { it.id }
                    ) { expense ->
                        ExpenseItem(expense = expense, onNavigateToNewExpenseScreen = {
                            //TODO: handle navigation for update expense here.
                        })
                    }
                }
            }
            //TODO: show empty list view.
        }
    }

}