package com.zillotrix.moneytracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.zillotrix.moneytracker.core.navigation.AppNavGraph
import com.zillotrix.moneytracker.core.navigation.LocalNavActions
import com.zillotrix.moneytracker.core.navigation.NavActions
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme

@Composable
fun App(){
    val navController = rememberNavController()
    val navActions = remember(navController) { NavActions(navController) }
    MoneyTrackerTheme{
        CompositionLocalProvider(LocalNavActions provides navActions) {
            AppNavGraph(navController)
        }
    }
}