package com.zillotrix.moneytracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zillotrix.moneytracker.features.base.presentation.ui.BaseScreen
import com.zillotrix.moneytracker.features.budget.presentation.ui.NewBudgetScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.BaseScreen.route) {
        composable(Screen.BaseScreen.route) {
            BaseScreen(
                onNavigateToNewBudgetScreen = {
                    navController.navigate(Screen.NewBudgetScreen.route)
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
    }
}