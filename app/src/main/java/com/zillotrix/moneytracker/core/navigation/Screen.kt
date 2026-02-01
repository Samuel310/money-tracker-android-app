package com.zillotrix.moneytracker.core.navigation

sealed class Screen(val route: String) {
    object BaseScreen : Screen("base_screen")
    object NewBudgetScreen : Screen("new-budget-screen")
}