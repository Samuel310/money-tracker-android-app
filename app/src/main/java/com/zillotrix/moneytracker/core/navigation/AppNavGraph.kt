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
        composable(Screen.BaseScreen.route) {
            BaseScreen(
                onNavigateToNewBudgetScreen = {
                    navController.navigate(Screen.NewBudgetScreen.route)
                },
                onNavigateExpenseScreen = {budgetId, yearMonth ->
                    navController.navigate(Screen.ExpenseScreen.route + "/$budgetId/$yearMonth")
                }
            )
        }
        composable(Screen.NewBudgetScreen.route) {
            NewBudgetScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            Screen.ExpenseScreen.route + "/{budgetId}/{yearMonth}",
            arguments = listOf(
                navArgument("budgetId") { type = NavType.LongType },
                navArgument("yearMonth") { type = NavType.IntType },
            )
        ) {
            ExpenseScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNewExpenseScreen = {budgetId, yearMonth ->
                    navController.navigate(Screen.NewExpenseScreen.route + "/$budgetId/$yearMonth")
                }
            )
        }
        composable(
            Screen.NewExpenseScreen.route + "/{budgetId}/{yearMonth}",
            arguments = listOf(
                navArgument("budgetId") { type = NavType.LongType },
                navArgument("yearMonth") { type = NavType.IntType },
            )
        ) {
            NewExpenseScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}