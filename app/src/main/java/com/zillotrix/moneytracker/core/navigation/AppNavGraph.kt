package com.zillotrix.moneytracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zillotrix.moneytracker.features.base.presentation.components.BaseScreen
import com.zillotrix.moneytracker.features.budget.presentation.components.NewBudgetScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.BaseScreen.route) {
        composable(Screen.BaseScreen.route) { BaseScreen(navController) }
        composable(Screen.NewBudgetScreen.route) { NewBudgetScreen(navController) }
    }
}