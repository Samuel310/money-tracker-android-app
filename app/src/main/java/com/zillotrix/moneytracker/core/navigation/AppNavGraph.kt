package com.zillotrix.moneytracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zillotrix.moneytracker.features.base.presentation.ui.BaseScreen
import com.zillotrix.moneytracker.features.budget.presentation.ui.NewBudgetScreen
import com.zillotrix.moneytracker.features.expenses.presentation.ui.ExpenseScreen
import com.zillotrix.moneytracker.features.expenses.presentation.ui.NewExpenseScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.BaseScreen.route) {
        composable(Screen.BaseScreen.route) { BaseScreen() }
        composable(Screen.NewBudgetScreen.route) { NewBudgetScreen() }
        composable(
            Screen.ExpenseScreen.route + "/{budgetId}/{yearMonth}",
            arguments = listOf(
                navArgument("budgetId") { type = NavType.LongType },
                navArgument("yearMonth") { type = NavType.IntType },
            )
        ) { ExpenseScreen() }
        composable(
            Screen.NewExpenseScreen.route + "/{budgetId}/{yearMonth}?expenseId={expenseId}",
            arguments = listOf(
                navArgument("budgetId") { type = NavType.LongType },
                navArgument("yearMonth") { type = NavType.IntType },
                navArgument("expenseId") {
                    type = NavType.LongType
                    defaultValue = -1L
                },
            )
        ) { NewExpenseScreen() }
    }
}