package com.zillotrix.moneytracker.features.budget.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zillotrix.moneytracker.core.navigation.LocalNavActions
import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.core.utils.toMonthName
import com.zillotrix.moneytracker.core.utils.toYearString
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.MonthPickerDialog
import com.zillotrix.moneytracker.features.budget.presentation.view_model.BudgetScreenViewModel
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.BudgetInfoCard
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.ExpandableFab
import kotlinx.coroutines.launch

@Composable
fun BudgetScreen(budgetScreenViewModel: BudgetScreenViewModel = hiltViewModel<BudgetScreenViewModel>()){

    val context = LocalContext.current
    val navActions = LocalNavActions.current
    val state by budgetScreenViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            budgetScreenViewModel.onError.collect { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    if(state.showMonthPickerDialog){
        MonthPickerDialog(
            selectedYearMonth = state.currentYearMonth,
            onDismiss = { yearMonth ->
                if(yearMonth != null){
                    budgetScreenViewModel.onMonthChanged(yearMonth = yearMonth)
                }
                budgetScreenViewModel.showMonthPickerDialog(show = false)
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            ExpandableFab(
                isExpanded = state.isFabExpanded,
                onMainFabClick = {
                    budgetScreenViewModel.toggleFab(!state.isFabExpanded)
                },
                onAddBudgetClick = {
                    budgetScreenViewModel.toggleFab(false)
                    navActions.navigateToNewBudgetScreen()
                },
                onAddExpenseClick = {
                    if(state.budgetInfoMap.isEmpty()){
                        Toast.makeText(context, "Please create a budget first before adding an expense.", Toast.LENGTH_SHORT).show()
                        return@ExpandableFab
                    }
                    budgetScreenViewModel.toggleFab(false)
                    navActions.navigateToNewExpenseScreen(budgetId = 0L, yearMonth = state.currentYearMonth.toIntYYYYMM(), expenseId = null)
                }
            )
        }
    ) { innerPadding ->
        //TODO: show loader
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .height(45.dp)
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    budgetScreenViewModel.onMonthChanged(state.currentYearMonth.minusMonths(1))
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Previous Month"
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {
                            budgetScreenViewModel.showMonthPickerDialog(show = true)
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.currentYearMonth.toMonthName(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = state.currentYearMonth.toYearString(),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                IconButton(onClick = {
                    budgetScreenViewModel.onMonthChanged(state.currentYearMonth.plusMonths(1))
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Next Month"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .height(45.dp)
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Income: ",
                )
                Text(
                    text = "5000",
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Income"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    "Budget",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            if(state.budgetInfoMap.isNotEmpty()){
                for(categoryName in state.budgetInfoMap.keys){
                    Text(categoryName,  fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 8.dp, end = 8.dp))
                    state.budgetInfoMap[categoryName]?.forEach { budgetInfo ->
                        BudgetInfoCard(budgetInfo)
                    }
                }
            }
        }
    }
}