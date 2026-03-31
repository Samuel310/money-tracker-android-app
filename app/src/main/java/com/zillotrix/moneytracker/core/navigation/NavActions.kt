package com.zillotrix.moneytracker.core.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {
    fun navigateBack(){ navController.popBackStack() }

    fun navigateToNewBudgetScreen(yearMonth : Int){
        navController.navigate("${Screen.NewBudgetScreen.route}/$yearMonth")
    }

    fun navigateToExpenseScreen(budgetId : Long, yearMonth : Int){
        navController.navigate("${Screen.ExpenseScreen.route}/$budgetId/$yearMonth")
    }

    fun navigateToNewExpenseScreen(budgetId : Long, yearMonth : Int, expenseId : Long?){
        var route = "${Screen.NewExpenseScreen.route}/$budgetId/$yearMonth"
        if (expenseId != null && expenseId > 0L) {
            route += "?expenseId=$expenseId"
        }
        navController.navigate(route)
    }
}

val LocalNavActions = staticCompositionLocalOf<NavActions> {
    error("No NavActions provided! Check your CompositionLocalProvider.")
}