package com.zillotrix.moneytracker.features.budget.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.zillotrix.moneytracker.core.utils.toDisplayAmount
import com.zillotrix.moneytracker.core.utils.toIntYYYYMM
import com.zillotrix.moneytracker.core.utils.toMonthName
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.CurrentMonthSection
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.MonthPickerDialog
import com.zillotrix.moneytracker.features.budget.presentation.view_model.BudgetScreenViewModel
import com.zillotrix.moneytracker.features.expenses.presentation.ui.common.BudgetInfoCard
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.ExpandableFab
import com.zillotrix.moneytracker.features.budget.presentation.ui.common.MonthlyOverallPSAView
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
                    navActions.navigateToNewBudgetScreen(state.currentYearMonth.toIntYYYYMM())
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            CurrentMonthSection(
                currentYearMonth = state.currentYearMonth,
                onMonthChanged = { newMonth ->
                    budgetScreenViewModel.onMonthChanged(newMonth)
                },
                onSelectMonthBtnClicked = {
                    budgetScreenViewModel.showMonthPickerDialog(show = true)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if(state.isBudgetOverviewLoading || state.isBudgetInfoMapLoading){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }
            }else{
                if(state.budgetInfoMap.isNotEmpty()){
                    if(state.budgetOverview != null){
                        MonthlyOverallPSAView(
                            plannedAmt = state.budgetOverview?.totalBudget ?: 0L,
                            spentAmt = state.budgetOverview?.totalSpentAmt ?: 0L,
                            availableAmt = state.budgetOverview?.totalAvailableAmt ?: 0L,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        state.budgetInfoMap.forEach { (categoryName, budgetInfoList) ->
                            item(key = categoryName) {
                                Row(
                                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                ) {
                                    Text(categoryName,  fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(state.budgetOverview?.categories[categoryName]?.totalPlannedAmount?.toDisplayAmount() ?: "")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("(${state.budgetOverview?.categories[categoryName]?.percentage}%)")
                                }
                            }
                            items(
                                items = budgetInfoList,
                                key = {it.id}
                            ) { budgetInfo ->
                                BudgetInfoCard(budgetInfo)
                            }
                        }
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "No budgets planned for ${state.currentYearMonth.toMonthName()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Start fresh by creating a new plan, or save time by importing your most recent budget settings.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                navActions.navigateToNewBudgetScreen(state.currentYearMonth.toIntYYYYMM())
                            },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text("Create New Plan")
                        }
                        OutlinedButton(
                            onClick = {
                                budgetScreenViewModel.setBudgetFromMostRecentBudget()
                            },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text("Import Most Recent Plan")
                        }
                    }
                }
            }
        }
    }
}